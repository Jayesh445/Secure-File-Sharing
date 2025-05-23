package com.secure.FileShareApp.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.secure.FileShareApp.annotation.FileIdParam;
import com.secure.FileShareApp.annotation.LogAction;
import com.secure.FileShareApp.entity.AuditAction;
import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.exceptions.ResourceNotFoundException;
import com.secure.FileShareApp.repository.UploadedFileRepository;
import com.secure.FileShareApp.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;
    private final UploadedFileRepository uploadedFileRepository;

    @Override
    public String uploadFile(MultipartFile file, String userId, String folderPath ) {
        try {
            String finalPath = (folderPath==null || folderPath.isEmpty()) ? "user_files/"+userId+"/":"user_files/"+userId+"/"+folderPath;
            String resourceType = file.getContentType() != null && file.getContentType().startsWith("image")
                    ? "image"
                    : "raw";
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                    "folder", finalPath,
                    "resource_type", resourceType
            ));
            return uploadResult.get("public_id").toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
    }

    @Override
    public boolean deleteFile( String filePath ) {
        try {
//            String publicId = (folderPath==null || folderPath.isEmpty()) ? "user_files/"+userId+"/"+fileName:"user_files/"+userId+"/"+folderPath+"/"+fileName;
            Map<?, ?> result = cloudinary.uploader().destroy(filePath, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file"+e.getMessage());
        }
    }

    @Override
    public boolean moveFile( String userId, String fileName, String oldFolder, String newFolder) {
        try {
            String publicId = oldFolder.substring(oldFolder.lastIndexOf("/")+1);
            System.out.println("publicId"+publicId);
            System.out.println("oldFolder"+oldFolder);
            System.out.println("newFolder "+newFolder);
            String newPublicId = "user_files/" + userId + "/" + newFolder;
            Map<?, ?> result = cloudinary.uploader().rename(oldFolder, newPublicId, ObjectUtils.emptyMap());
            return "ok".equals(result.get("result"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to move file", e);
        }
    }

    @Override
    @LogAction(action = AuditAction.FOLDER_ACTION)
    public boolean deleteFolder(String folderPath) {
        try {
            cloudinary.api().deleteResourcesByPrefix(folderPath,ObjectUtils.emptyMap());
            cloudinary.api().deleteFolder(folderPath,ObjectUtils.emptyMap());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete folder"+ e.getMessage());
        }
    }

    @Override
    @LogAction(action = AuditAction.FOLDER_ACTION)
    public boolean createFolder(String userId, String folderPath) {
        File tempFile = null;
        try {
            String fullPath = "user_files/" + userId + "/" + folderPath;
            System.out.println("folderPath "+fullPath);
            tempFile = File.createTempFile("temp", ".txt");
            FileWriter writer = new FileWriter(tempFile);
            writer.write("Temporary file to create folder");
            writer.close();
            Map<?,?> response = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
                    "folder", fullPath,
                    "resource_type", "raw"
            ));

            System.out.println("Upload successful: " + response);
            cloudinary.uploader().destroy(fullPath, ObjectUtils.emptyMap());
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create folder"+ e.getMessage());
        }finally {
            if(tempFile!=null && tempFile.exists()) {
                System.out.println(tempFile.delete());
            }
        }
    }

    // TODO -- NOT WORKING PROPERLY
    @Override
    @LogAction(action = AuditAction.EDIT_FILE)
    public boolean copyFile(@FileIdParam String fileId, String destinationPath) {
        UploadedFile file = uploadedFileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File not found"));
        String oldPublicId = file.getFilePath();
        String newPublicId = destinationPath + "/" + file.getFileName();

        try {
            Map<?, ?> result = cloudinary.uploader()
                    .upload(oldPublicId, ObjectUtils.asMap(
                            "public_id", newPublicId,
                            "overwrite", false
                    ));
            if ("ok".equals(result.get("result"))) {
                file.setFilePath(newPublicId);
                uploadedFileRepository.save(file);
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy file "+ e.getMessage());
        }
    }

    @Override
    @LogAction(action = AuditAction.VIEW_FILE)
    public String generateFilePreview(@FileIdParam String fileId) {
        UploadedFile uploadedFile = uploadedFileRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
        return cloudinary.url()
                .transformation(new Transformation<>().quality(50))
                .generate(uploadedFile.getFilePath());
    }

    @Override
    @LogAction(action = AuditAction.DOWNLOAD_FILE)
    public String generateSignedDownloadLink(String publicId, int expiryMinutes) {
        try {
            long expiryTime = System.currentTimeMillis() + (long) expiryMinutes * 60 * 1000;
             Map<String, Object> options = Map.of(
                    "sign_url", true,
                    "expires_at", expiryTime,
                    "public_ids", publicId
            );

            return cloudinary.downloadArchive(options,"zip");
        }catch (Exception e) {
            throw new RuntimeException("Failed to generate signed download link"+ e.getMessage());
        }
    }

    @Override
    @LogAction(action = AuditAction.DOWNLOAD_FILE)
    public String generateSignedZipDownloadLink(List<String> publicIds, int expiryMinutes) {
        try {
            long expiryTime = (System.currentTimeMillis() / 1000) + (expiryMinutes * 60L);

            Map<String, Object> options = Map.of(
                    "expires_at", expiryTime,
                    "sign_url", true,
                    "format", "zip",
                    "public_ids", publicIds
            );
            return cloudinary.downloadArchive(options, "zip");

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ZIP download link: " + e.getMessage(), e);
        }
    }


    @Override //TODO
    public boolean restoreDeletedFile(String fileId) {
        return false;
    }
}
