package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.PermissionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilePermissionRequestDto {

    @NotBlank(message = "File ID cannot be empty")
    private String fileId;

//    @NotBlank(message = "User ID cannot be empty")
    private String userId;

    @NotNull(message = "Permission Type is required")
    private PermissionType permissionType;

}
