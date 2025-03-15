package com.secure.FileShareApp.service;

import com.secure.FileShareApp.entity.PermissionType;

import java.io.IOException;
import java.util.List;

public interface FileSharingService {

    boolean shareFileViaEmail(String fileId, String senderId, String recipientEmail, PermissionType permissionType);

    String generateOneTimeDownloadLink(String fileId, int expiryMinutes);

    String generateZipDownloadLink(List<String> fileIds, int expiryMinutes)throws IOException;
}
