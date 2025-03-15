package com.secure.FileShareApp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthResponseDto {
    private String token;
    private final String tokenType = "Bearer";
    private UserResponseDto userDto;

    public AuthResponseDto(String token,UserResponseDto userDto) {
        this.token = token;
        this.userDto = userDto;
    }
}
