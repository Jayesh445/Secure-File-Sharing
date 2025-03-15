package com.secure.FileShareApp.controller;

import com.secure.FileShareApp.dto.AuthResponseDto;
import com.secure.FileShareApp.dto.LoginDto;
import com.secure.FileShareApp.dto.UserDto;
import com.secure.FileShareApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.authenticateUser(loginDto));
    }
}
