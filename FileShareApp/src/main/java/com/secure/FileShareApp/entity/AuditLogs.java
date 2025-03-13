package com.secure.FileShareApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String logId;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    @NotNull(message = "Audit file cannot be null")
    private UploadedFile auditFile;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User cannot be null")
    private User user;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Action type cannot be null")
    private AuditAction action;

    @Size(max = 500, message = "Message cannot exceed 500 characters")
    private String message;

    private LocalDateTime timestamp = LocalDateTime.now();
}
