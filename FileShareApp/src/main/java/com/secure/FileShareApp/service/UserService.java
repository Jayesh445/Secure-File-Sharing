package com.secure.FileShareApp.service;

import com.secure.FileShareApp.dto.AuthResponseDto;
import com.secure.FileShareApp.dto.LoginDto;
import com.secure.FileShareApp.dto.UserDto;
import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public  interface UserService extends UserDetailsService {

     AuthResponseDto registerUser(UserDto userDto);

     AuthResponseDto authenticateUser(LoginDto loginDto);

     UserResponseDto updateUser(UserDto userDto);

     void deleteUser(UserDto userDto);

     UserResponseDto getUserByEmail(String email);

     User getUserById(String userId);

     List<UserResponseDto> getAllUsers();

     UserResponseDto changePassword(UserDto userDto);

     void deactivateUser(String userId);

     void reactivateUser(String userId);

}

