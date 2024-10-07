package com.ardwiinoo.loansapi.model.dto.user;

import jakarta.validation.constraints.NotBlank;

public class UserRefreshTokenRequest {
    @NotBlank
    private String refreshToken;
}
