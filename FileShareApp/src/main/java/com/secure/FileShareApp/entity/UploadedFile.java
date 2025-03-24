package com.secure.FileShareApp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String fileId;

    @NotNull(message = "File name cannot be null")
    @Size(min = 3, max = 255, message = "File name must be between 3 and 255 characters")
    private String fileName;

    @NotNull(message = "File type cannot be null")
    @Size(max = 50, message = "File type cannot exceed 50 characters")
    private String fileType;

    @NotNull(message = "File path cannot be null")
    @Size(min = 5, max = 500, message = "File path must be between 5 and 500 characters")
    private String filePath;

    private String folderPath;

    @NotNull(message = "File size cannot be null")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?\\s?(KB|MB|GB|TB)$",
            message = "Invalid file size format (e.g., 10.5 MB, 250 KB)")
    private String fileSize;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User uploadedBy;

    @OneToMany(mappedBy = "auditFile", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<AuditLogs> auditLogs = new ArrayList<>();

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<FilePermission> filePermissions = new ArrayList<>();

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<RequestAccess> requestAccesses = new ArrayList<>();


}
