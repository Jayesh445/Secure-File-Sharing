package com.secure.FileShareApp.service;

import com.secure.FileShareApp.entity.UploadedFile;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadedFileService {

    UploadedFile uploadFile(MultipartFile file, String userId, String folderPath);

    List<UploadedFile> uploadMultipleFiles(List<MultipartFile> files, String userId,String folderPath);

    UploadedFile getFileById(String fileId);

    Page<UploadedFile> getFileByUserId(String userId,int page, int size);

    Page<UploadedFile> getSharedFileByUserId(String userId,int page, int size);

    boolean deleteFile(String fileId);

    UploadedFile updateFileMetadata(UploadedFile uploadedFile);

    List<UploadedFile> searchFiles(String query,String userId, int page, int size);

    boolean moveFile(String fileId, String newFolder);

}
