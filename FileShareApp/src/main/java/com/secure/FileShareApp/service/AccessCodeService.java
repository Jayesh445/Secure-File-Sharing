package com.secure.FileShareApp.service;

import com.secure.FileShareApp.entity.PermissionType;

public interface AccessCodeService {

    String generateAccessCode(String fileId, String userId, PermissionType permissionType, int expiryMinutes);

    boolean verifyAccessCode(String fileId, String userId, String accessCode);

}
