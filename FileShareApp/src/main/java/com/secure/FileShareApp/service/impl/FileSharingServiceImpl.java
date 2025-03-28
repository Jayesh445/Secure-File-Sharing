package com.secure.FileShareApp.service.impl;

import com.secure.FileShareApp.annotation.FileIdParam;
import com.secure.FileShareApp.annotation.LogAction;
import com.secure.FileShareApp.entity.AuditAction;
import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.PermissionType;
import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.exceptions.ResourceNotFoundException;
import com.secure.FileShareApp.repository.FilePermissionRepository;
import com.secure.FileShareApp.repository.UploadedFileRepository;
import com.secure.FileShareApp.repository.UserRepository;
import com.secure.FileShareApp.service.CloudinaryService;
import com.secure.FileShareApp.service.FileSharingService;
import com.secure.FileShareApp.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FileSharingServiceImpl implements FileSharingService {

    private final FilePermissionRepository filePermissionRepository;
    private final UploadedFileRepository uploadedFileRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final EmailUtils emailUtils;

    @Override
    public boolean shareFileViaEmail(String fileId, String senderId, String recipientEmail, PermissionType permissionType) {
         User recipient = userRepository.findByEmail(recipientEmail)
                 .orElseThrow(() -> new ResourceNotFoundException("User with email " + recipientEmail + " not found"));

         UploadedFile uploadedFile = uploadedFileRepository.findById(fileId)
                 .orElseThrow(() -> new ResourceNotFoundException("File with id " + fileId + " not found"));

        filePermissionRepository.findByFile_FileIdAndUser_UserIdAndPermissionTypesContaining(fileId, senderId,PermissionType.OWNER)
                .orElseThrow(() -> new ResourceNotFoundException("Sender does not have permission to share this file"));

        Optional<FilePermission> existingPermission = filePermissionRepository.findByFile_FileIdAndUser_UserIdAndPermissionTypesContaining(fileId, recipient.getUserId(),permissionType);
        if (existingPermission.isPresent()) {
            List<PermissionType> permissionTypes = existingPermission.get().getPermissionTypes();
            if (!permissionTypes.contains(permissionType)) {
                permissionTypes.add(permissionType);
                existingPermission.get().setPermissionTypes(permissionTypes);
                filePermissionRepository.save(existingPermission.get());
            }
        }else {
            FilePermission newFilePermission = new FilePermission();
            newFilePermission.setFile(uploadedFile);
            newFilePermission.setUser(recipient);
            newFilePermission.setPermissionTypes(List.of(permissionType));
            filePermissionRepository.save(newFilePermission);
        }

        String url = cloudinaryService.generateFilePreview(fileId);

        String htmlContent = loadHtmlTemplate()
                .replace("{{PERMISSION_TYPE}}", permissionType.name())
                .replace("{{FILE_NAME}}", uploadedFile.getFileName())
                .replace("{{FILE_URL}}", uploadedFile.getFilePath());

        emailUtils.sendEmailWithStreamAttachment(
                recipientEmail,
                "File Access Granted",
                htmlContent,
                url,
                uploadedFile.getFileName()
        );
        return true;
    }

    @Override
    @LogAction(action = AuditAction.DOWNLOAD_FILE)
    public String generateOneTimeDownloadLink(@FileIdParam String fileId, int expiryMinutes) {
        UploadedFile file = uploadedFileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found: " + fileId));
        return cloudinaryService.generateSignedDownloadLink(file.getFilePath(), expiryMinutes);
    }

    @Override
    @LogAction(action = AuditAction.DOWNLOAD_FILE)
    public String generateZipDownloadLink(List<String> fileIds, int expiryMinutes)  {
        List<String> publicIds = fileIds.stream()
                .map(id -> uploadedFileRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("File not found: " + id))
                        .getFilePath())
                .toList();

        return cloudinaryService.generateSignedZipDownloadLink(publicIds, expiryMinutes);
    }

    @Override
    @Transactional
    @LogAction(action = AuditAction.DOWNLOAD_FILE)
    public String generateShareableLink(@FileIdParam String fileId, PermissionType permissionType, int expiryMinutes) {
        System.out.println(" expiry -------  "+expiryMinutes);
        UploadedFile file = uploadedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String token = UUID.randomUUID().toString();
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(expiryMinutes);
        FilePermission filePermission = new FilePermission();
        filePermission.setFile(file);
//        filePermission.setUser(user);
        filePermission.setShareToken(token);
        filePermission.setExpireTime(expireTime);
        filePermission.setPermissionTypes(List.of(permissionType));
        filePermissionRepository.save(filePermission);
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return  baseUrl + "/share/access?token=" + token;
    }

    @Override
    @LogAction(action = AuditAction.VIEW_FILE)
    public String getFileFromShareableLink(String token) {
        FilePermission filePermission = filePermissionRepository.findByShareToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid link"));
        if (filePermission.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Shareable link has expired");
        }
        return cloudinaryService.generateFilePreview(filePermission.getFile().getFileId());
    }

    private String loadHtmlTemplate() {
        ClassPathResource resource = new ClassPathResource("templates/email-template.html");
        try {
            return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load template"+ e.getMessage());
        }
    }
}
