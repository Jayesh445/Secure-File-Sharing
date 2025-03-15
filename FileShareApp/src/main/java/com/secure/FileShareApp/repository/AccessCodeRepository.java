package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessCodeRepository extends JpaRepository<AccessCode, Long> {

    Optional<AccessCode> findByFileIdAndUserId(String fileId, String userId);

    void deleteByFileIdAndUserId(String fileId, String userId);
}
