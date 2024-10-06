package com.ardwiinoo.loansapi.model.dto.user;

import lombok.Data;

@Data
public class UserTokenResponse {
    private String accessToken;
    private String refreshToken;
}
