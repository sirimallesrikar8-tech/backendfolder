package com.eventapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ===============================
    // JWT CONFIG FROM application.properties
    // ===============================
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private Key key;

    // ===============================
    // INITIALIZE SIGNING KEY
    // ===============================
    @PostConstruct
    public void init() {
        if (secret == null || secret.length() < 32) {
            throw new RuntimeException(
                    "JWT secret must be at least 32 characters long"
            );
        }

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    // =====================================================
    // ✅ GENERATE TOKEN
    // =====================================================
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)              // user email
                .claim("role", role)            // ADMIN / USER / VENDOR
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + expirationTime)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // =====================================================
    // ✅ EXTRACT ALL CLAIMS
    // =====================================================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // =====================================================
    // ✅ EXTRACT EMAIL
    // =====================================================
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // =====================================================
    // ✅ EXTRACT ROLE
    // =====================================================
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // =====================================================
    // ✅ CHECK TOKEN EXPIRATION
    // =====================================================
    public boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // =====================================================
    // ✅ VALIDATE TOKEN
    // =====================================================
    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email)
                && !isTokenExpired(token);
    }
}
