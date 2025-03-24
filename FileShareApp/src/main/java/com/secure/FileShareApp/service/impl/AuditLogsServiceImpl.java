package com.secure.FileShareApp.service.impl;

import com.secure.FileShareApp.annotation.FileIdParam;
import com.secure.FileShareApp.annotation.LogAction;
import com.secure.FileShareApp.annotation.UserIdParam;
import com.secure.FileShareApp.dto.AuditLogDto;
import com.secure.FileShareApp.entity.AuditAction;
import com.secure.FileShareApp.entity.AuditLogs;
import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.exceptions.ResourceNotFoundException;
import com.secure.FileShareApp.repository.AuditLogsRepository;
import com.secure.FileShareApp.repository.UploadedFileRepository;
import com.secure.FileShareApp.service.AuditLogsService;
import com.secure.FileShareApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditLogsServiceImpl implements AuditLogsService {

    private final AuditLogsRepository auditLogsRepository;
    private final UserService userService;
    private final UploadedFileRepository uploadedFileRepository;

    @Override
    public void logAction(String userId, String fileId, AuditAction action, String message) {
        User user = userService.getUserById(userId);
        UploadedFile file = uploadedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found with id: " + fileId));
        AuditLogs auditLog = new AuditLogs();
        auditLog.setUser(user);
        auditLog.setAuditFile(file);
        auditLog.setAction(action);
        auditLog.setMessage(message);
        auditLog.setTimestamp(LocalDateTime.now());

        auditLogsRepository.save(auditLog);
    }

    @Override
    @LogAction(action = AuditAction.VIEW_AUDIT_LOG)
    public List<AuditLogDto> getAuditLogsForFile(@FileIdParam String fileId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<AuditLogs> auditLogs = auditLogsRepository.findByAuditFile_FileId(fileId, pageable);
        return auditLogs.getContent().stream()
                .map(AuditLogDto::new)
                .toList();
    }

    @Override
    @LogAction(action = AuditAction.VIEW_AUDIT_LOG)
    public List<AuditLogDto> getAuditLogsForUser(@UserIdParam String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<AuditLogs> auditLogs = auditLogsRepository.findByUser_UserId(userId, pageable);
        return auditLogs.getContent().stream()
                .map(AuditLogDto::new)
                .toList();
    }
}
