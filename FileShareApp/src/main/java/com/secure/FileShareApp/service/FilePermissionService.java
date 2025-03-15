package com.secure.FileShareApp.service;

import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.PermissionType;

import java.time.LocalDateTime;
import java.util.List;

public interface FilePermissionService {

    FilePermission grantFilePermission(String fileId, String userId, PermissionType permissionType);

    FilePermission revokeFilePermission(String fileId, String userId, PermissionType permissionType);

    FilePermission grantPermissionWithExpiry(String fileId, String userId, PermissionType permissionType, LocalDateTime expireTime);

    void revokeExpiryPermission();

    String generateShareableLink(String fileId, PermissionType permissionType,int expiryMinutes );

    String getFileFromShareableLink(String token );

    List<UserResponseDto> getUsersWithFileAccess(String fileId);

    List<FilePermission> getFilePermission(String fileId);

    boolean hasFilePermission(String fileId, String userId, PermissionType permissionType);

    FilePermission updateFilePermission(String fileId, String userId, PermissionType permissionType);

}
