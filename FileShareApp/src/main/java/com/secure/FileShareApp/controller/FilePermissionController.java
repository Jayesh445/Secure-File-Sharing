package com.secure.FileShareApp.controller;

import com.secure.FileShareApp.dto.FilePermissionDto;
import com.secure.FileShareApp.dto.FilePermissionRequestDto;
import com.secure.FileShareApp.dto.FilePermissionWithExpiryRequestDto;
import com.secure.FileShareApp.dto.UserWithPermissionsDto;
import com.secure.FileShareApp.service.FilePermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file-permissions")
public class FilePermissionController {

    private final FilePermissionService filePermissionService;

    @PostMapping("/grant")
    public ResponseEntity<FilePermissionDto> grantPermission(
            @RequestBody @Valid FilePermissionRequestDto filePermissionRequestDto) {
        FilePermissionDto filePermission = filePermissionService.grantFilePermission(
                filePermissionRequestDto.getFileId(),
                filePermissionRequestDto.getUserId(),
                filePermissionRequestDto.getPermissionType()
        );
        return ResponseEntity.ok(filePermission);
    }

    @PostMapping("/grant-with-expiry")
    public ResponseEntity<FilePermissionDto> grantPermissionWithExpiry(
            @RequestBody @Valid FilePermissionWithExpiryRequestDto requestDto
    ) {
        FilePermissionDto filePermission = filePermissionService.grantPermissionWithExpiry(
                requestDto.getFileId(), requestDto.getUserId(), requestDto.getPermissionType(), requestDto.getExpiryTime()
        );
        return ResponseEntity.ok(filePermission);
    }

    @DeleteMapping("/revoke")
    public ResponseEntity<String> revokePermission(
            @RequestBody @Valid FilePermissionRequestDto requestDto) {
        String message = filePermissionService.revokeFilePermission(
                requestDto.getFileId(), requestDto.getUserId(), requestDto.getPermissionType()
                );
        return ResponseEntity.ok(message);
    }

    @GetMapping("/users/{fileId}")
    public ResponseEntity<List<UserWithPermissionsDto>> getUsersWithFileAccess(@PathVariable String fileId) {
        List<UserWithPermissionsDto> users = filePermissionService.getUsersWithFileAccess(fileId);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<List<FilePermissionDto>> getFilePermissions(@PathVariable String fileId) {
        List<FilePermissionDto> filePermissions = filePermissionService.getFilePermission(fileId);
        return ResponseEntity.ok(filePermissions);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasFilePermission(
            @RequestBody @Valid FilePermissionRequestDto requestDto) {
        boolean hasPermission = filePermissionService.hasFilePermission(
                requestDto.getFileId(), requestDto.getUserId(), requestDto.getPermissionType()
        );
        return ResponseEntity.ok(hasPermission);
    }

}
