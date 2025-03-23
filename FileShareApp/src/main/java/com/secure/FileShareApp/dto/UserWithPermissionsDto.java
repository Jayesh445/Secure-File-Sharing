package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.PermissionType;
import com.secure.FileShareApp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserWithPermissionsDto {
    private UserResponseDto user;
    private List<PermissionType> permissions;
}

