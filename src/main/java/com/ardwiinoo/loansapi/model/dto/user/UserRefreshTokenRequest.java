package com.ardwiinoo.loansapi.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRefreshTokenRequest {
    @NotBlank
    private String refreshToken;
}
