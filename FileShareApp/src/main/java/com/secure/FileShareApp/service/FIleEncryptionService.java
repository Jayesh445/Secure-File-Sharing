package com.secure.FileShareApp.service;

public interface FIleEncryptionService {

    String encryptFile(String fileId, String encryptionKey);

    String decryptFile(String fileId, String encryptionKey);

    boolean isFileEncrypted(String fileId);
}
