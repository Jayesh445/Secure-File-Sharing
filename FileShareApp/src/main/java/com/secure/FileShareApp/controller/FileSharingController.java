package com.secure.FileShareApp.controller;

import com.secure.FileShareApp.dto.FilePermissionRequestDto;
import com.secure.FileShareApp.dto.FilePermissionWithExpiryRequestDto;
import com.secure.FileShareApp.service.FileSharingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/share")
public class FileSharingController {

    private final FileSharingService fileSharingService;

    @PostMapping("/generate-link")
    public ResponseEntity<String> generateShareableLink(
            @RequestBody @Valid FilePermissionWithExpiryRequestDto requestDto) {
        LocalDateTime expiryTime = requestDto.getExpiryTime();
        LocalDateTime now = LocalDateTime.now();
        int expiryTimeInMinutes = (int) Duration.between(now, expiryTime).toMinutes();
        String link = fileSharingService.generateShareableLink(
                requestDto.getFileId(), requestDto.getPermissionType(),expiryTimeInMinutes
        );
        return ResponseEntity.ok(link);
    }

    @GetMapping("/access")
    public ResponseEntity<String> getFileFromShareableLink(@RequestParam String token) {
        String file = fileSharingService.getFileFromShareableLink(token);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(file))
                .build();
    }

    @PostMapping("/email/{email}")
    public ResponseEntity<String> sendEmail(
            @RequestBody @Valid FilePermissionRequestDto requestDto,
            @PathVariable String email,
            HttpServletRequest request
    ){
        if(fileSharingService.shareFileViaEmail(requestDto.getFileId(), requestDto.getUserId(), email, requestDto.getPermissionType(),request)){
            return ResponseEntity.ok("Email sent successfully");
        }else {
            return ResponseEntity.ok("Email could not be sent");
        }
    }

    @GetMapping("/{fileId}/download-link")
    public ResponseEntity<String> generateOneTimeDownloadLink(
            @PathVariable String fileId,
            @RequestParam(defaultValue = "15") int expiryMinutes) {

        String downloadLink = fileSharingService.generateOneTimeDownloadLink(fileId, expiryMinutes);
        return ResponseEntity.ok(downloadLink);
    }

    @PostMapping("/zip-download-link")
    public ResponseEntity<String> generateZipDownloadLink(
            @RequestBody List<String> fileIds,
            @RequestParam(defaultValue = "15") int expiryMinutes) {

        String zipDownloadLink = fileSharingService.generateZipDownloadLink(fileIds, expiryMinutes);
        return ResponseEntity.ok(zipDownloadLink);
    }
}
