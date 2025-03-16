package com.secure.FileShareApp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.exceptions.ResourceNotFoundException;
import com.secure.FileShareApp.repository.UploadedFileRepository;
import com.secure.FileShareApp.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    private final UploadedFileRepository uploadedFileRepository;

    @Override
    public String uploadFile(MultipartFile file,String userId,String folderPath ) {
        try {
            String finalPath = (folderPath==null || folderPath.isEmpty()) ? "user_files/"+userId+"/":"user_files/"+userId+"/"+folderPath;
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", finalPath));
            return uploadResult.get("public_id").toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public boolean deleteFile(String userId,String folderPath,String fileName ) {
        try {
            String publicId = (folderPath==null || folderPath.isEmpty()) ? "user_files/"+userId+"/"+fileName:"user_files/"+userId+"/"+folderPath+"/"+fileName;
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }

    @Override
    public boolean moveFile(String userId, String fileName, String oldFolder, String newFolder) {
        try {
            String oldPublicId = "user_files/" + userId + "/" + oldFolder + "/" + fileName;
            String newPublicId = "user_files/" + userId + "/" + newFolder + "/" + fileName;
            Map<?, ?> result = cloudinary.uploader().rename(oldPublicId, newPublicId, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to move file", e);
        }
    }

    @Override
    public boolean deleteFolder(String folderPath) {
        try {
            cloudinary.api().deleteResourcesByPrefix(folderPath,ObjectUtils.emptyMap());
            cloudinary.api().deleteFolder(folderPath,ObjectUtils.emptyMap());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete folder", e);
        }
    }

    @Override
    public boolean createFolder(String userId, String folderPath) {
        try {
            String fullPath = "user_files/" + userId + "/" + folderPath;
            cloudinary.uploader().upload(new byte[0], ObjectUtils.asMap("folder", fullPath));
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create folder", e);
        }
    }

    @Override
    public boolean copyFile(String fileId, String destinationPath) {
        UploadedFile file = uploadedFileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File not found"));
        String oldPublicId = file.getFilePath();
        String newPublicId = destinationPath + "/" + file.getFileName();

        try {
            Map<?, ?> result = cloudinary.uploader().rename(oldPublicId, newPublicId, ObjectUtils.asMap("overwrite", true));
            if ("ok".equals(result.get("result"))) {
                file.setFilePath(newPublicId);
                uploadedFileRepository.save(file);
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy file", e);
        }
    }

    @Override
    public String generateFilePreview(String fileId) {
        UploadedFile uploadedFile = uploadedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        return cloudinary.url()
                .transformation(new Transformation<>().quality(50))
                .generate(uploadedFile.getFilePath());
    }

    @Override //TODO
    public boolean restoreDeletedFile(String fileId) {
        return false;
    }
}
