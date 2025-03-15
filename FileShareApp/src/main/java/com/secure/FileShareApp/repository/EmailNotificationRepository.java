package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.EmailNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, String> {
}
