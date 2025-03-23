package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.PermissionType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FilePermissionDto {
    private String filePermissionId;
    private String fileId;
    private String userId;
    private List<PermissionType> permissionType;
    private String shareToken;
    private LocalDateTime expireTime;

    public FilePermissionDto(FilePermission filePermission) {
        this.filePermissionId = filePermission.getFilePermissionId();
        this.userId=filePermission.getUser().getUserId();
        this.fileId=filePermission.getFile().getFileId();
        this.permissionType = filePermission.getPermissionTypes();
        this.shareToken = filePermission.getShareToken();
        this.expireTime = filePermission.getExpireTime();
    }
}
