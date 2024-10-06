package com.ardwiinoo.loansapi.service.impl;

import com.ardwiinoo.loansapi.service.JWTService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.secret.accessTokenKey}")
    private String accessTokenSecret;

    @Value("${jwt.secret.refreshTokenKey}")
    private String refreshTokenSecret;

    @Value("${jwt.secret.accessTokenExp}")
    private String accessTokenExp;

    @Value("${jwt.secret.refreshTokenExp}")
    private String refreshTokenExp;

    private static final Logger log = LoggerFactory.getLogger(JWTServiceImpl.class);

    private SecretKey getKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date getExp(String exp) {
        return new Date(System.currentTimeMillis() + Long.parseLong(exp) * 1000);
    }

    @Override
    public String generateAccessToken(String username) {
        log.info("JWTService.generateAccessToken, time = {}", LocalDateTime.now());

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(getExp(accessTokenExp))
                .signWith(getKey(accessTokenSecret))
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        log.info("JWTService.generateRefreshToken, time = {}", LocalDateTime.now());

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(getExp(refreshTokenExp))
                .signWith(getKey(refreshTokenSecret))
                .compact();
    }

    @Override
    public String extractUsernameFromAccessToken(String accessToken) {
        return Jwts.parser()
                .verifyWith(getKey(accessTokenSecret))
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getSubject();
    }

    @Override
    public String extractUsernameFromRefreshToken(String refreshToken) {
        return Jwts.parser()
                .verifyWith(getKey(refreshTokenSecret))
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getSubject();
    }

    @Override
    public Boolean validateAccessToken(String accessToken, UserDetails userDetails) {
        String username = extractUsernameFromAccessToken(accessToken);
        return (username.equals(userDetails.getUsername())) && isTokenExpired(accessToken, accessTokenSecret);
    }

    @Override
    public Boolean validateRefreshToken(String refreshToken) {
        return isTokenExpired(refreshToken, refreshTokenSecret);
    }

    @Override
    public Boolean isTokenExpired(String token, String secret) {
        Date tokenExp = Jwts.parser()
                .verifyWith(getKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return tokenExp.before(new Date());
    }
}
