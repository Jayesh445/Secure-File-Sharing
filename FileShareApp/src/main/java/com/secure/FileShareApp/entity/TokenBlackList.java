package com.secure.FileShareApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TokenBlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tokenId;
    private String token;

    public TokenBlackList(String token) {
        this.token = token;
    }

}
