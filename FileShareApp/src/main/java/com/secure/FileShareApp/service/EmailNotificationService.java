package com.secure.FileShareApp.service;

import com.secure.FileShareApp.entity.EmailNotification;

import java.util.List;

public interface EmailNotificationService {

    void sendNotification(String userId, String subject, String message);

    List<EmailNotification> getNotificationsForUser(String userId, int page, int size);

    void markNotificationAsRead(String notificationId);

}
