package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.FilePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilePermissionRepository extends JpaRepository<FilePermission, String> {

}
