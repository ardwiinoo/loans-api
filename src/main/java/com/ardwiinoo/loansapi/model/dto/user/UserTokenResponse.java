package com.ardwiinoo.loansapi.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTokenResponse {
    private String accessToken;
    private String refreshToken;
}
