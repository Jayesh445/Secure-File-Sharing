package com.secure.FileShareApp.service;

public interface TrashService {

    boolean moveToTrash(String fileId, String userId);

    boolean emptyTrash(String userId);

    boolean restoreFile(String fileId, String userId);

}
