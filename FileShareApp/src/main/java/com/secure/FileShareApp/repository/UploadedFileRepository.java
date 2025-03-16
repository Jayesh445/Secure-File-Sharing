package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.UploadedFile;
import com.secure.FileShareApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, String> {

    Optional<UploadedFile> findByFileName(String fileName);

    Page<UploadedFile> findUploadedFilesByUploadedBy(User uploadedBy, Pageable pageable);

    @Query("SELECT f FROM UploadedFile f " +
            "LEFT JOIN FilePermission p ON f.fileId = p.file.fileId " +
            "WHERE (LOWER(f.fileName) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(f.fileType) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(f.folderPath) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(f.filePath) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND (f.uploadedBy.userId = :userId OR p.user.userId = :userId)")
    List<UploadedFile> searchByFileNameOrFileTypeOrFilePathOrFolderPath(@Param("query") String query,
                                                            @Param("userId") String userId,
                                                            PageRequest pageRequest);
}
