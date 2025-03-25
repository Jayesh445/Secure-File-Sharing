package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.AuditAction;
import com.secure.FileShareApp.entity.AuditLogs;
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
    private String fileName;
    private String userId;
    private String username;
    private AuditAction action;
    private String message;
    private LocalDateTime timestamp;

    public AuditLogDto(AuditLogs auditLogs) {
        this.logId = auditLogs.getLogId();
        if (auditLogs.getAuditFile() != null) {
            this.fileId = auditLogs.getAuditFile().getFileId();
            this.fileName = auditLogs.getAuditFile().getFileName();
        }
        if (auditLogs.getUser() != null) {
            this.userId = auditLogs.getUser().getUserId();
            this.username = auditLogs.getUser().getUsername();
        }
        this.action = auditLogs.getAction();
        this.message = auditLogs.getMessage();
        this.timestamp = auditLogs.getTimestamp();
    }
}
