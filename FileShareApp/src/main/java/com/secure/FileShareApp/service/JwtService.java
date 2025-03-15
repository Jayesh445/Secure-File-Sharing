package com.secure.FileShareApp.service;

import com.secure.FileShareApp.entity.User;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String extractUsername(String token);

    <T> T getClaim(String token, Function<Claims,T> claimResolver);

    String generateToken(User user);

    String generateToken(Map<String,Object> extraClaims, User user);

    boolean isTokenValid(String token, User user);

    boolean isTokenExpired(String token);

    Claims getClaimsFromToken(String token);

    Key getSignInKeyFromToken();
}
