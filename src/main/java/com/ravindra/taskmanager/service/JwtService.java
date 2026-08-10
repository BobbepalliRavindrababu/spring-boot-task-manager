package com.ravindra.taskmanager.service;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private SecretKey secretKey;
    private long expiration;

    public JwtService(@Value ("${jwt.secretKey}") String secret, @Value ("${jwt.expiration}") long expiration){
        this.secretKey=Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration=expiration;
    }

    public String generateToken(String email){

        Date now=new Date();

        Date expire=new Date(now.getTime()+expiration);

        return  Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expire)
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
