package com.secure.FileShareApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String filePermissionId;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    @NotNull(message = "File cannot be null")
    private UploadedFile file;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(targetClass = PermissionType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "file_permission_types", joinColumns = @JoinColumn(name = "file_permission_id"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Permission type cannot be null")
    private List<PermissionType> permissionTypes=new ArrayList<>();


    private String shareToken;

    private LocalDateTime expireTime;
}
