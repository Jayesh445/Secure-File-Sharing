package com.secure.FileShareApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private UploadedFile file;

    @ManyToOne
    @JoinColumn(name = "user_id" ,nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;
}
