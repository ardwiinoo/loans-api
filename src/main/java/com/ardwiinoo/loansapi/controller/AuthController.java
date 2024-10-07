package com.ardwiinoo.loansapi.controller;

import com.ardwiinoo.loansapi.model.dto.user.UserDto;
import com.ardwiinoo.loansapi.model.dto.user.UserLoginRequest;
import com.ardwiinoo.loansapi.model.dto.user.UserRegisterRequest;
import com.ardwiinoo.loansapi.model.dto.user.UserTokenResponse;
import com.ardwiinoo.loansapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> userRegisterHandler(@RequestBody UserRegisterRequest request) {
        UserDto result = authService.userRegister(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "status", "success",
                "data", Map.of(
                        "userId", result.getId()
                )
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> userLoginHandler(@RequestBody UserLoginRequest request) {
        UserTokenResponse result = authService.userLogin(request);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "status", "success",
                "data", result
                )
        );
    }
}
