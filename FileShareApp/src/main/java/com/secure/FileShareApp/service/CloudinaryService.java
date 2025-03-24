package com.secure.FileShareApp.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    String generateSignedDownloadLink(String publicId, int expiryMinutes);

    String generateSignedZipDownloadLink(List<String> publicIds, int expiryMinutes);
}
