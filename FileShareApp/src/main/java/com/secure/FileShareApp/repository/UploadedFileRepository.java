package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile, String> {
}
