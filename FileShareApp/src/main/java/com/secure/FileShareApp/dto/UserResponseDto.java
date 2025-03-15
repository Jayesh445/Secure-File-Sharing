package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.Role;
import com.secure.FileShareApp.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private  String userId;

    @NotNull(message = "userName cannot be null")
    @Size(min=3,max=30,message = "Username must be between 3 to 30 characters")
    private String username;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;

    private LocalDateTime createdAt;

    private Role role;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.role = user.getRole();
    }

}
