package com.secure.FileShareApp.service;

import com.secure.FileShareApp.dto.FilePermissionDto;
import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.dto.UserWithPermissionsDto;
import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.PermissionType;

import java.time.LocalDateTime;
import java.util.List;

public interface FilePermissionService {

    FilePermissionDto grantFilePermission(String fileId, String userId, PermissionType permissionType);

    String revokeFilePermission(String fileId, String userId, PermissionType permissionType);

    FilePermissionDto grantPermissionWithExpiry(String fileId, String userId, PermissionType permissionType, LocalDateTime expireTime);

    void revokeExpiryPermission();

    String generateShareableLink(String fileId, PermissionType permissionType,int expiryMinutes );

    String getFileFromShareableLink(String token );

    List<UserWithPermissionsDto> getUsersWithFileAccess(String fileId);

    List<FilePermissionDto> getFilePermission(String fileId);

    boolean hasFilePermission(String fileId, String userId, PermissionType permissionType);

}
