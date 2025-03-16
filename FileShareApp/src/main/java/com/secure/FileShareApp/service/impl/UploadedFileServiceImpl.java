package com.secure.FileShareApp.service.impl;

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
    public UploadedFile uploadFile(MultipartFile file, String userId,String folderPath) {
        User user = userService.getUserById(userId);
        String filePath = cloudinaryService.uploadFile(file,userId,folderPath);
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFilePath(filePath);
        uploadedFile.setFolderPath(folderPath);
        uploadedFile.setFileName(file.getOriginalFilename());
        uploadedFile.setFileType(file.getContentType());
        uploadedFile.setUploadedBy(user);
        uploadedFile.setFileSize(FileUtils.formatFileSize(file.getSize()));
        return uploadedFileRepository.save(uploadedFile);
    }

    @Override
    public List<UploadedFile> uploadMultipleFiles(List<MultipartFile> files, String userId,String folderPath) {
        List<UploadedFile> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            UploadedFile uploadedFile = uploadFile(file, userId,folderPath);
            uploadedFiles.add(uploadedFile);
        }
        return uploadedFiles;
    }

    @Override
    public UploadedFile getFileById(String fileId) {
        return  uploadedFileRepository.findById(fileId).orElseThrow(
                () -> new RuntimeException("File not found")
        );
    }

    @Override
    public Page<UploadedFile> getFileByUserId(String userId, int page, int size) {
        User user = userService.getUserById(userId);
        PageRequest pageRequest= PageRequest.of(page, size);
       return uploadedFileRepository.findUploadedFilesByUploadedBy(user,pageRequest);
    }

    @Override
    public Page<UploadedFile> getSharedFileByUserId(String userId, int page, int size) {
        User user = userService.getUserById(userId);
        PageRequest pageRequest= PageRequest.of(page, size);
        Page<FilePermission> sharedFilePermissions = filePermissionRepository.findByUser(user, pageRequest);
        List<UploadedFile> sharedFiles = sharedFilePermissions
                .map(FilePermission::getFile)
                .stream().distinct().toList();
        return new PageImpl<>(sharedFiles,pageRequest,sharedFilePermissions.getTotalElements());
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
    public UploadedFile updateFileMetadata(UploadedFile uploadedFile) {
        UploadedFile uploadedFile1 = uploadedFileRepository.findById(uploadedFile.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        uploadedFile1.setFileName(uploadedFile.getFileName());
        uploadedFile1.setFileType(uploadedFile.getFileType());
        uploadedFile1.setUploadedBy(uploadedFile.getUploadedBy());
        uploadedFile1.setFileSize(uploadedFile.getFileSize());
        uploadedFile1.setFilePath(uploadedFile.getFilePath());
        return uploadedFileRepository.save(uploadedFile1);
    }

    @Override
    public List<UploadedFile> searchFiles(String query, String userId, int page, int size) {
        User user = userService.getUserById(userId);
        PageRequest pageRequest= PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdAt"));
        return  uploadedFileRepository.searchByFileNameOrFileTypeOrFilePathOrFolderPath(query,user.getUserId(),pageRequest);
    }

    @Override
    public boolean moveFile(String fileId, String newPath) {
        Optional<UploadedFile> fileOpt = uploadedFileRepository.findById(fileId);
        if (fileOpt.isEmpty()) {
            throw new ResourceNotFoundException("File not found");
        }
        UploadedFile file = fileOpt.get();
        String oldPublicId = file.getFilePath();
        String newPublicId = newPath + "/" + file.getFileName();
        return cloudinaryService.moveFile(oldPublicId,newPublicId,file.getFolderPath(),file.getFileName());
    }

}
