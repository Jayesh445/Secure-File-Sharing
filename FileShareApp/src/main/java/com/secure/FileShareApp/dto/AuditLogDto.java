package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDto {
    private String logId;
    private String fileId;
    private String fileName;  // Useful for UI display
    private String userId;
    private String username;
    private AuditAction action;
    private String message;
    private LocalDateTime timestamp;
}
