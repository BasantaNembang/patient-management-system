package com.security.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    private final Key secretKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
         byte [] bytes = Base64.getDecoder()
                 .decode(secret.getBytes(StandardCharsets.UTF_8));
         this.secretKey = Keys.hmacShaKeyFor(bytes);
    }


    public String getJwtToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *10)) // 10 hours
                .signWith(secretKey)
                .compact();
    }


    public void validateToken(String jwtToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(jwtToken);
        } catch (Exception e) {
            log.error("error while validating the token {} ", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
