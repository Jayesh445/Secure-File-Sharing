package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.EmailNotification;
import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.RequestAccess;
import com.secure.FileShareApp.entity.Role;
import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Set<Role> roles = new HashSet<>();

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.roles = user.getRoles();
    }

}
