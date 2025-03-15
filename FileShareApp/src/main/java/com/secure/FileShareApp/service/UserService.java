package com.secure.FileShareApp.service;

import com.secure.FileShareApp.dto.AuthResponseDto;
import com.secure.FileShareApp.dto.LoginDto;
import com.secure.FileShareApp.dto.RoleAssignmentDto;
import com.secure.FileShareApp.dto.UserDto;
import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.RoleType;
import com.secure.FileShareApp.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public  interface UserService extends UserDetailsService {

     ResponseEntity<AuthResponseDto> registerUser(UserDto userDto);

     ResponseEntity<AuthResponseDto> authenticateUser(LoginDto loginDto);

     ResponseEntity<UserResponseDto> updateUser(UserDto userDto);

     ResponseEntity<?> deleteUser(UserDto userDto);

     ResponseEntity<UserResponseDto> getUserByEmail(String email);

     ResponseEntity<User> getUserById(String userId);

     ResponseEntity<List<UserResponseDto>> getAllUsers();

     ResponseEntity<UserResponseDto> changePassword(UserDto userDto);

     ResponseEntity<RoleAssignmentDto> assignRoleToUser(UserDto userDto, RoleType role);
}

