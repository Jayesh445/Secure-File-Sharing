package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.PermissionType;
import com.secure.FileShareApp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FilePermissionRepository extends JpaRepository<FilePermission, String> {

    Page<FilePermission> findByUser(User user, Pageable pageable);

    Optional<FilePermission> findByFile_FileIdAndUser_UserId_AndPermissionTypesIsContaining(String fileId, String userId, PermissionType permissionType);

    List<FilePermission> findByExpireTimeBefore(LocalDateTime expireTime);

    List<FilePermission> findByFile_FileId(String fileId);

    boolean existsByFile_FileIdAndUser_UserIdAndPermissionTypesIsContaining(String fileId, String userId, PermissionType permissionType);

    List<FilePermission> findByFile_FileIdAndUser_UserId(String fileId, String userId);
}
