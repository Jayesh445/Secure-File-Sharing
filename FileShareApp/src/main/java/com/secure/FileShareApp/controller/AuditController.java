package com.secure.FileShareApp.controller;

import com.secure.FileShareApp.dto.AuditLogDto;
import com.secure.FileShareApp.service.AuditLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditLogsService auditService;

    @GetMapping("/file/{fileId}")
    public ResponseEntity<List<AuditLogDto>> getAuditLogsForFile(
            @PathVariable String fileId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AuditLogDto> logs = auditService.getAuditLogsForFile(fileId, page, size);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogDto>> getAuditLogsForUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<AuditLogDto> logs = auditService.getAuditLogsForUser(userId, page, size);
        return ResponseEntity.ok(logs);
    }
}

