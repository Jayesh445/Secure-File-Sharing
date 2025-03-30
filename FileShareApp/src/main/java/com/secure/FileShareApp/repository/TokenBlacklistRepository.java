package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.TokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlackList,String> {
    boolean existsByToken(String token);
}
