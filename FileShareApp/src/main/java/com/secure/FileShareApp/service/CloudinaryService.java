package com.secure.FileShareApp.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String uploadFile(MultipartFile file,String userId,String folderPath);

    boolean deleteFile(String publicId,String folderPath,String fileName);

    boolean moveFile(String userId, String fileName, String oldFolder, String newFolder);

    boolean deleteFolder(String folderPath);

    boolean createFolder(String userId, String folderPath);

    boolean copyFile(String fileId, String destinationPath);

    String generateFilePreview(String fileId);

    // TODO
    boolean restoreDeletedFile(String fileId);
}
