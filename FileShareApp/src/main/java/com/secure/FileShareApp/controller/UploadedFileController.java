package com.secure.FileShareApp.controller;

import com.secure.FileShareApp.dto.UploadedFileDto;
import com.secure.FileShareApp.service.CloudinaryService;
import com.secure.FileShareApp.service.UploadedFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class UploadedFileController {

    private final CloudinaryService cloudinaryService;
    private final UploadedFileService uploadedFileService;

    @PostMapping("/upload")
    public ResponseEntity<UploadedFileDto> uploadFile(
            @RequestBody MultipartFile file,
            @RequestParam String userId,
            @RequestParam(value = "folderPath",required = false) String folderPath
    ) {
        UploadedFileDto uploadedFileDto = uploadedFileService.uploadFile(file, userId, folderPath);
        return ResponseEntity.ok(uploadedFileDto);
    }

    @PostMapping("/upload-multiple")
    public ResponseEntity<List<UploadedFileDto>> uploadMultipleFiles(
            @RequestParam String userId,
            @RequestBody List<MultipartFile> files,
            @RequestParam(value = "folderPath",required = false) String folderPath
    ){
        List<UploadedFileDto> uploadedFileDtos = uploadedFileService.uploadMultipleFiles(files, userId, folderPath);
        return ResponseEntity.ok(uploadedFileDtos);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<UploadedFileDto> getFileById(@PathVariable String fileId) {
        UploadedFileDto uploadedFileDto = uploadedFileService.getFileById(fileId);
        return ResponseEntity.ok(uploadedFileDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<UploadedFileDto>> getFilesByUserId(
            @PathVariable String userId,
            @RequestParam(value = "page",required = false,defaultValue = "0") int page,
            @RequestParam(value = "size",required = false,defaultValue = "20") int size
    ) {
       Page<UploadedFileDto> uploadedFileDtoPage = uploadedFileService.getFileByUserId(userId, page, size);
       return ResponseEntity.ok(uploadedFileDtoPage);
    }

    // TODO -- testing remaining only due to share feature still need to be added
    @GetMapping("/shared/{userId}")
    public ResponseEntity<Page<UploadedFileDto>> getSharedFiles(
            @PathVariable String userId,
            @RequestParam(value = "page",required = false,defaultValue = "0") int page,
            @RequestParam(value = "size",required = false,defaultValue = "20") int size
    ) {
        Page<UploadedFileDto> sharedFileByUserId = uploadedFileService.getSharedFileByUserId(userId, page, size);
        return ResponseEntity.ok(sharedFileByUserId);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        if (!uploadedFileService.deleteFile(fileId)){
            return ResponseEntity.ok("Failed to delete file with id " + fileId);
        }
        return ResponseEntity.ok("Deleted file with id " + fileId);
    }

    @PutMapping("/move/{fileId}")
    public ResponseEntity<String> moveFile(
            @PathVariable String fileId,
            @RequestParam String newFolder
    ){
        if(!uploadedFileService.moveFile(fileId, newFolder)){
            return ResponseEntity.ok("Failed to move file with id " + fileId+" to "+newFolder);
        }
        return ResponseEntity.ok("Moved file with id " + fileId+" to "+newFolder);
    }

    @PutMapping("/metadata")
    public ResponseEntity<UploadedFileDto> updateFileMetadata(UploadedFileDto uploadedFileDto) {
        UploadedFileDto updated = uploadedFileService.updateFileMetadata(uploadedFileDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/search/user/{userId}")
    public ResponseEntity<Page<UploadedFileDto>> searchFiles(
            @PathVariable String userId,
            @RequestParam String query,
            @RequestParam(value = "page",required = false,defaultValue = "0") int page,
            @RequestParam(value = "size",required = false,defaultValue = "20") int size
    ){
        Page<UploadedFileDto> searchedFiles = uploadedFileService.searchFiles(userId, query, page, size);
        return ResponseEntity.ok(searchedFiles);
    }

    @DeleteMapping("/delete-folder")
    public ResponseEntity<String> deleteFolder(@RequestParam String folderPath) {
        if(!cloudinaryService.deleteFolder(folderPath)){
            return ResponseEntity.ok("Failed to delete folder " + folderPath);
        }
        return ResponseEntity.ok("Deleted folder " + folderPath);
    }

    @PostMapping("/create-folder")
    public ResponseEntity<String> createFolder(
            @RequestParam String userId,
            @RequestParam String folderPath
    ) {
        if(!cloudinaryService.createFolder(userId, folderPath)){
            return ResponseEntity.ok("Failed to create folder " + folderPath);
        }
        return ResponseEntity.ok("Created folder " + folderPath);
    }

    @PutMapping("/copy")
    public ResponseEntity<String> copyFile(
            @RequestBody String fileId,
            @RequestParam String destinationPath
    ) {
        if(!cloudinaryService.copyFile(fileId, destinationPath)){
            return ResponseEntity.ok("Failed to copy file " + fileId + " to " + destinationPath);
        }
        return ResponseEntity.ok("Copied file " + fileId + " to " + destinationPath);
    }

    @GetMapping("/preview")
    public ResponseEntity<String> getFilePreview(@RequestParam String fileId) {
        String previewUrl = cloudinaryService.generateFilePreview(fileId);
        return ResponseEntity.ok(previewUrl);
    }
}
