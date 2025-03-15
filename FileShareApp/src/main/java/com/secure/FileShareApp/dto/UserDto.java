package com.secure.FileShareApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String userId;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 30, message = "Name must be between 3 to 30 characters")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;
}
