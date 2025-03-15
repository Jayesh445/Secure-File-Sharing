package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.AuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogsRepository extends JpaRepository<AuditLogs, String> {

}
