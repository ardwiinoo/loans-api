package com.ardwiinoo.loansapi.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateAccessToken(String username);
    String generateRefreshToken(String username);
    String extractUsernameFromAccessToken(String accessToken);
    String extractUsernameFromRefreshToken(String refreshToken);
    Boolean validateAccessToken(String accessToken, UserDetails userDetails);
    Boolean validateRefreshToken(String refreshToken);
    Boolean isTokenExpired(String token, String secret);
}
