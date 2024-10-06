package com.ardwiinoo.loansapi.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequest {

    @Email
    @NotBlank
    private String email;

    @Size(min = 8)
    @NotBlank
    private String password;
}
