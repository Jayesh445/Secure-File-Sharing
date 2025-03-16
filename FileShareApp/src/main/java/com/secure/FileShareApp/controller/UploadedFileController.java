package com.secure.FileShareApp.controller;

import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.service.CloudinaryService;
import com.secure.FileShareApp.service.UploadedFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class UploadedFileController {

    private final CloudinaryService cloudinaryService;
    private final UploadedFileService uploadedFileService;

    @PostMapping("/upload")
    public ResponseEntity<UploadedFile> uploadFile(
            @RequestBody MultipartFile file,
            @RequestParam String userId,
            @RequestParam(value = "folderPath",required = false) String folderPath) {
        UploadedFile uploadedFile = uploadedFileService.uploadFile(file, userId, folderPath);
        return ResponseEntity.ok(uploadedFile);
    }
}
