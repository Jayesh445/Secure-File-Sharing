package com.secure.FileShareApp.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String userId;

    @NotNull(message = "userName cannot be null")
    @Size(min=3,max=30,message = "Username must be between 3 to 30 characters")
    private String userName;

    @NotNull(message = "Password cannot be null")
    @Length(min = 8,message = "Password should be of Minimum 8 characters")
    private String password;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    private String email;
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role should be defined")
    private Role role;

    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UploadedFile> files = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<FilePermission> filePermissions = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EmailNotification> senderEmail = new ArrayList<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EmailNotification> recipientEmail = new ArrayList<>();

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<RequestAccess> requestAccesses = new ArrayList<>();
}
