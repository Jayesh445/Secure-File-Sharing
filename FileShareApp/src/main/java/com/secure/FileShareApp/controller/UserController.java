package com.secure.FileShareApp.controller;

import com.secure.FileShareApp.dto.UserDto;
import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserDto userDto) {
        UserResponseDto updatedUser = userService.updateUser(userDto);
        return  ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserDto userDto) {
        userService.deleteUser(userDto);
        return ResponseEntity.ok("Deleted user with email "+userDto.getEmail()+"successfully");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto user = userService.getUserByEmail(email);
        return  ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> allUsers = userService.getAllUsers();
        return  ResponseEntity.ok(allUsers);
    }

    @GetMapping
    public ResponseEntity<User> getUserById(@RequestParam String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}
