package com.secure.FileShareApp.service;

import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.RoleType;

import java.util.List;

public interface RoleService {

    UserResponseDto makeAdmin(String userId);

    UserResponseDto revokeAdmin(String userId);

    List<UserResponseDto>listUsersByRole(RoleType roleType);
}
