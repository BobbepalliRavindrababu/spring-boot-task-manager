package com.ravindra.taskmanager.service;

import com.ravindra.taskmanager.entity.User;
import com.ravindra.taskmanager.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long expiration;
    private final UserRepository userRepository;

    public JwtService(
            @Value("${jwt.secretKey}") String secret,
            @Value("${jwt.expiration}") long expiration,
            UserRepository userRepository
    ) {

        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
        this.userRepository = userRepository;
    }

    public String generateToken(String email) {

        Date now = new Date();

        Date expire = new Date(now.getTime() + expiration);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return Jwts.builder()
                .subject(email)
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expire)
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}