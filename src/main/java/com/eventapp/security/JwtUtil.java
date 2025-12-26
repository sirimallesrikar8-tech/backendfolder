package com.eventapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔐 SECRET KEY
    // IMPORTANT:
    // 1. Must be LONG (at least 32 characters)
    // 2. Must NEVER change once tokens are issued
    private static final String SECRET =
            "eventapp-super-secure-secret-key-1234567890";

    // 🔑 Signing key created from secret
    private final Key key = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8)
    );

    // ⏰ Token validity: 10 hours
    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 10;

    // =====================================================
    // ✅ GENERATE TOKEN
    // =====================================================
    public String generateToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)              // username/email
                .claim("role", role)            // ADMIN / USER / VENDOR
                .setIssuedAt(new Date())        // now
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
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
    // ✅ EXTRACT EMAIL (SUBJECT)
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
        String tokenEmail = extractEmail(token);
        return tokenEmail.equals(email) && !isTokenExpired(token);
    }
}
