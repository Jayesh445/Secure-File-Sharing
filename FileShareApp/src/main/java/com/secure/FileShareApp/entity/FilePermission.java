package com.secure.FileShareApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePermission {
    @Id
    private String filePermissionId;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    @NotNull(message = "File cannot be null")
    private UploadedFile file;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Permission type cannot be null")
    private PermissionType permissionType;
}
