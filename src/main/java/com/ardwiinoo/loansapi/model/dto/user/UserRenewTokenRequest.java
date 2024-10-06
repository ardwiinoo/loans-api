package com.ardwiinoo.loansapi.model.dto.user;

import jakarta.validation.constraints.NotBlank;

public class UserRenewTokenRequest {
    @NotBlank
    private String refreshToken;
}
