package com.secure.FileShareApp.dto;

public class AuthResponseDto {
    private String token;
    private final String tokenType = "Bearer";
    private UserResponseDto userDto;
}
