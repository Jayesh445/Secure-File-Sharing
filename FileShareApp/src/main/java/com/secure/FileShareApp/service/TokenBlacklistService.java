package com.secure.FileShareApp.service;

public interface TokenBlacklistService {

    String blacklistToken(String token);

    boolean isBlacklisted(String token);
}
