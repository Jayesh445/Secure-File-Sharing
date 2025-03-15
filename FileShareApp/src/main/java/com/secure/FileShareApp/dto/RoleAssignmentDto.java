package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleAssignmentDto {

    private UserResponseDto userResponseDto;
    private Role role;
}
