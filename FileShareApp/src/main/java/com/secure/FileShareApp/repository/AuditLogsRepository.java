package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.AuditLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogsRepository extends JpaRepository<AuditLogs, String> {

    Page<AuditLogs> findByAuditFile_FileId(String fileId, Pageable pageable);

    Page<AuditLogs> findByUser_UserId(String userId, Pageable pageable);

}
