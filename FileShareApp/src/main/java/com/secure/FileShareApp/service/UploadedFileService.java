package com.secure.FileShareApp.service;

import com.secure.FileShareApp.dto.UploadedFileDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadedFileService {

    UploadedFileDto uploadFile(MultipartFile file, String userId, String folderPath);

    List<UploadedFileDto> uploadMultipleFiles(List<MultipartFile> files, String userId,String folderPath);

    UploadedFileDto getFileById(String fileId);

    Page<UploadedFileDto> getFileByUserId(String userId,int page, int size);

    Page<UploadedFileDto> getSharedFileByUserId(String userId,int page, int size);

    boolean deleteFile(String fileId);

    UploadedFileDto updateFileMetadata(UploadedFileDto uploadedFileDto);

    Page<UploadedFileDto> searchFiles(String query,String userId, int page, int size);

    boolean moveFile(String fileId, String newFolder);

}
