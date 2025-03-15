package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.Role;

import java.util.Set;

public class UserResponseDto {
    private String userId;
    private String userName;
    private String email;
    private Set<Role> roles;
}
