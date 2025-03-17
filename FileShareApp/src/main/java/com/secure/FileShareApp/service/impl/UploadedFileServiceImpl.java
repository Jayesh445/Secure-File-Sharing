package com.secure.FileShareApp.service.impl;

import com.secure.FileShareApp.dto.UploadedFileDto;
import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.exceptions.ResourceNotFoundException;
import com.secure.FileShareApp.repository.FilePermissionRepository;
import com.secure.FileShareApp.repository.UploadedFileRepository;
import com.secure.FileShareApp.service.CloudinaryService;
import com.secure.FileShareApp.service.UploadedFileService;
import com.secure.FileShareApp.service.UserService;
import com.secure.FileShareApp.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UploadedFileServiceImpl implements UploadedFileService {

    private final UploadedFileRepository uploadedFileRepository;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final FilePermissionRepository filePermissionRepository;


    @Override
    public UploadedFileDto uploadFile(MultipartFile file, String userId, String folderPath) {
        User user = userService.getUserById(userId);
        String filePath = cloudinaryService.uploadFile(file,userId,folderPath);
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFilePath(filePath);
        uploadedFile.setFolderPath(folderPath);
        uploadedFile.setFileName(file.getOriginalFilename());
        uploadedFile.setFileType(file.getContentType());
        uploadedFile.setUploadedBy(user);
        uploadedFile.setFileSize(FileUtils.formatFileSize(file.getSize()));
        UploadedFile saved = uploadedFileRepository.save(uploadedFile);
        return new UploadedFileDto(saved);
    }

    @Override
    public List<UploadedFileDto> uploadMultipleFiles(List<MultipartFile> files, String userId,String folderPath) {
        List<UploadedFileDto> uploadedFileDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadedFileDto uploadedFileDto = uploadFile(file, userId,folderPath);
            uploadedFileDtos.add(uploadedFileDto);
        }
        return uploadedFileDtos;
    }

    @Override
    public UploadedFileDto getFileById(String fileId) {
        UploadedFile uploadedFile = uploadedFileRepository.findById(fileId).orElseThrow(
                () -> new RuntimeException("File not found")
        );
        return new UploadedFileDto(uploadedFile);
    }

    @Override
    public Page<UploadedFileDto> getFileByUserId(String userId, int page, int size) {
        User user = userService.getUserById(userId);
        PageRequest pageRequest= PageRequest.of(page, size);
        Page<UploadedFile> paginatedFiles = uploadedFileRepository.findUploadedFilesByUploadedBy(user, pageRequest);
        return paginatedFiles.map(UploadedFileDto::new);
    }

    @Override
    public Page<UploadedFileDto> getSharedFileByUserId(String userId, int page, int size) {
        User user = userService.getUserById(userId);
        PageRequest pageRequest= PageRequest.of(page, size);
        Page<FilePermission> sharedFilePermissions = filePermissionRepository.findByUser(user, pageRequest);
        List<UploadedFileDto> sharedFileDtos = sharedFilePermissions
                .getContent().stream()
                .map(FilePermission::getFile)
                .distinct()
                .map(UploadedFileDto::new)
                .toList();
        return new PageImpl<>(sharedFileDtos,pageRequest,sharedFilePermissions.getTotalElements());
    }

    @Override
    public boolean deleteFile(String fileId) {
        UploadedFile uploadedFile = uploadedFileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        if(!cloudinaryService.deleteFile(uploadedFile.getFilePath(),uploadedFile.getFolderPath(),uploadedFile.getFileName())){
            throw new RuntimeException("Failed to delete file");
        }
        uploadedFileRepository.deleteById(fileId);
        return true;
    }

    @Override
    public UploadedFileDto updateFileMetadata(UploadedFileDto uploadedFileDto) {
        UploadedFile uploadedFile1 = uploadedFileRepository.findById(uploadedFileDto.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        uploadedFile1.setFileName(uploadedFileDto.getFileName());
        uploadedFile1.setFileType(uploadedFileDto.getFileType());
        uploadedFile1.setFileSize(uploadedFileDto.getFileSize());
        uploadedFile1.setFilePath(uploadedFileDto.getFilePath());
        UploadedFile saved = uploadedFileRepository.save(uploadedFile1);
        return new UploadedFileDto(saved);
    }

    @Override
    public Page<UploadedFileDto> searchFiles(String query, String userId, int page, int size) {
        User user = userService.getUserById(userId);
        PageRequest pageRequest= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdAt"));
        Page<UploadedFile> uploadedFiles = uploadedFileRepository.searchByFileNameOrFileTypeOrFilePathOrFolderPath(query, user.getUserId(), pageRequest);
        return uploadedFiles.map(UploadedFileDto::new );
    }

    @Override
    public boolean moveFile(String fileId, String newPath) {
        Optional<UploadedFile> fileOpt = uploadedFileRepository.findById(fileId);
        if (fileOpt.isEmpty()) {
            throw new ResourceNotFoundException("File not found");
        }
        UploadedFile file = fileOpt.get();
        return cloudinaryService.moveFile(file.getUploadedBy().getUserId(),file.getFileName(),file.getFilePath(),newPath);
    }

}
