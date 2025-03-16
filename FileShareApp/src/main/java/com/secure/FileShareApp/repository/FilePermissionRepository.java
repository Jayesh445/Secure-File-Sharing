package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilePermissionRepository extends JpaRepository<FilePermission, String> {

    Page<FilePermission> findByUser(User user, Pageable pageable);
}
