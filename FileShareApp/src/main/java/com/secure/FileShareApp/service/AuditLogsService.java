package com.secure.FileShareApp.service;

import com.secure.FileShareApp.dto.AuditLogDto;
import com.secure.FileShareApp.entity.AuditAction;

import java.util.List;

public interface AuditLogsService {

    void logAction(String userId, String fileId, AuditAction action, String message);

    List<AuditLogDto> getAuditLogsForFile(String fileId, int page, int size);

    List<AuditLogDto> getAuditLogsForUser(String userId, int page, int size);

}
