package com.secure.FileShareApp.dto;

import com.secure.FileShareApp.entity.UploadedFile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UploadedFileDto {

    private String fileId;
    private String fileName;
    private String fileType;
    private String filePath;
    private String folderPath;
    private String fileSize;
    private LocalDateTime createdAt;

    public UploadedFileDto(UploadedFile uploadedFile) {
        this.fileId = uploadedFile.getFileId();
        this.fileName = uploadedFile.getFileName();
        this.fileType = uploadedFile.getFileType();
        this.filePath = uploadedFile.getFilePath();
        this.folderPath = uploadedFile.getFolderPath();
        this.fileSize = uploadedFile.getFileSize();
        this.createdAt = uploadedFile.getCreatedAt();
    }

}
