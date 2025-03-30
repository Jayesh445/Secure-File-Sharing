package com.secure.FileShareApp.service.impl;

import com.secure.FileShareApp.entity.TokenBlackList;
import com.secure.FileShareApp.repository.TokenBlacklistRepository;
import com.secure.FileShareApp.service.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    public String blacklistToken(String token) {
        TokenBlackList tokenBlackList=tokenBlacklistRepository.save(new TokenBlackList(token));
        if(tokenBlackList.getToken()!=null){
            return "Logged out successfully";
        }else {
            return "Fail to log out";
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }
}

