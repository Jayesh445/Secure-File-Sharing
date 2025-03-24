package com.secure.FileShareApp.utils;

import com.secure.FileShareApp.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils{


    @Value("${SECRET_KEY}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(),user);
    }

    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKeyFromToken())
                .compact();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = getClaim(token,Claims::getSubject);
        return username.equals(user.getEmail());
    }

    public boolean isTokenExpired(String token) {
        return getClaim(token,Claims::getExpiration).before(new Date());
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKeyFromToken())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Key getSignInKeyFromToken() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
