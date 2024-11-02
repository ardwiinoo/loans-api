package com.ardwiinoo.loansapi.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserVerifyRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String verificationToken;
}
