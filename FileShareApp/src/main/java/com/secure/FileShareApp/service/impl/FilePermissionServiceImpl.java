package com.secure.FileShareApp.service.impl;

import com.secure.FileShareApp.annotation.FileIdParam;
import com.secure.FileShareApp.annotation.LogAction;
import com.secure.FileShareApp.annotation.UserIdParam;
import com.secure.FileShareApp.dto.FilePermissionDto;
import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.dto.UserWithPermissionsDto;
import com.secure.FileShareApp.entity.AuditAction;
import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.PermissionType;
import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.exceptions.ResourceNotFoundException;
import com.secure.FileShareApp.repository.FilePermissionRepository;
import com.secure.FileShareApp.repository.UploadedFileRepository;
import com.secure.FileShareApp.repository.UserRepository;
import com.secure.FileShareApp.service.FilePermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FilePermissionServiceImpl implements FilePermissionService {

    private final FilePermissionRepository filePermissionRepository;
    private final UploadedFileRepository uploadedFileRepository;
    private final UserRepository userRepository;

    @Override
    @LogAction(action = AuditAction.UPDATE_PERMISSIONS)
    public FilePermissionDto grantFilePermission(@FileIdParam String fileId, @UserIdParam String userId, PermissionType permissionType) {
        if(filePermissionRepository.existsByFile_FileIdAndUser_UserIdAndPermissionTypesIsContaining(fileId, userId, permissionType)){
            throw new RuntimeException("Permission already granted for file id " + fileId + " user id " + userId + " permission type " + permissionType);
        }
        FilePermission filePermission = extractFilePermission(fileId, userId, permissionType);
        FilePermission saved = filePermissionRepository.save(filePermission);
        return new FilePermissionDto(saved);
    }

    @Override
    @Transactional
    @LogAction(action = AuditAction.UPDATE_PERMISSIONS)
    public String revokeFilePermission(@FileIdParam String fileId,@UserIdParam String userId, PermissionType permissionType) {
        List<FilePermission> filePermissions = filePermissionRepository
                .findByFile_FileIdAndUser_UserId(fileId, userId);

        boolean permissionRevoked = false;

        for (FilePermission filePermission : filePermissions) {
            List<PermissionType> updatedPermissions = new ArrayList<>(filePermission.getPermissionTypes());

            if (!updatedPermissions.contains(permissionType)) {
                continue; // Skip if this FilePermission entry doesn't have the given permission type
            }

            System.out.println("Permissions before update: " + filePermission.getPermissionTypes());
            updatedPermissions.remove(permissionType);
            permissionRevoked = true;

            if (updatedPermissions.isEmpty()) {
                filePermissionRepository.delete(filePermission); // Remove entry if no permissions left
            } else {
                filePermission.setPermissionTypes(updatedPermissions);
                filePermissionRepository.save(filePermission);
            }
            System.out.println("Permissions after update: " + updatedPermissions);
        }

        if (!permissionRevoked) {
            throw new IllegalArgumentException("Permission type not found for this user and file.");
        }

        return "Permission " + permissionType + " revoked successfully";
    }



    @Override
    @LogAction(action = AuditAction.UPDATE_PERMISSIONS)
    public FilePermissionDto grantPermissionWithExpiry(@FileIdParam String fileId,@UserIdParam String userId, PermissionType permissionType, LocalDateTime expireTime) {
        FilePermission filePermission = extractFilePermission(fileId, userId, permissionType);
        filePermission.setExpireTime(expireTime);
        FilePermission saved = filePermissionRepository.save(filePermission);
        return new FilePermissionDto(saved);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @LogAction(action = AuditAction.UPDATE_PERMISSIONS)
    public void revokeExpiryPermission() {
        List<FilePermission> expiredPermissions = filePermissionRepository.findByExpireTimeBefore(LocalDateTime.now());

        if (!expiredPermissions.isEmpty()) {
            filePermissionRepository.deleteAll(expiredPermissions);
            log.info("Revoked {} expired file permissions.", expiredPermissions.size());
        }
    }


    @Override
    @LogAction(action = AuditAction.VIEW_FILE_ACCESS_LIST)
    public List<UserWithPermissionsDto> getUsersWithFileAccess(@FileIdParam String fileId) {
        return filePermissionRepository.findByFile_FileId(fileId).stream()
                .map(filePermission -> new UserWithPermissionsDto(
                        new UserResponseDto(filePermission.getUser()),
                        filePermission.getPermissionTypes() // Include permissions
                ))
                .toList();
    }

    @Override
    @LogAction(action = AuditAction.VIEW_FILE_PERMISSION)
    public List<FilePermissionDto> getFilePermission(@FileIdParam String fileId) {
        return filePermissionRepository.findByFile_FileId(fileId).stream()
                .map(FilePermissionDto::new)
                .toList();
    }

    @Override
    @LogAction(action = AuditAction.VIEW_FILE_PERMISSION)
    public boolean hasFilePermission(@FileIdParam String fileId,@UserIdParam String userId, PermissionType permissionType) {
        if (userId == null) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof User){
                userId = ((User) principal).getUserId();
            }else {
                return false;
            }
        }
        return filePermissionRepository.existsByFile_FileIdAndUser_UserIdAndPermissionTypesIsContaining(fileId,userId,permissionType);
    }

    private FilePermission extractFilePermission(String fileId, String userId, PermissionType permissionType) {
        UploadedFile uploadedFile = uploadedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        FilePermission filePermission = new FilePermission();
        filePermission.setFile(uploadedFile);
        filePermission.setUser(user);
        filePermission.setPermissionTypes(List.of(permissionType,PermissionType.VIEW));
        System.out.println(filePermission.getPermissionTypes());
        return filePermission;
    }
}
