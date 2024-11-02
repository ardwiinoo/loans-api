package com.ardwiinoo.loansapi.service;

import com.ardwiinoo.loansapi.model.dto.user.*;

public interface AuthService {
    UserDto userRegister(UserRegisterRequest request);
    UserTokenResponse userLogin(UserLoginRequest request);
    void userLogout(UserRefreshTokenRequest request);
    UserTokenResponse userRenewToken(UserRefreshTokenRequest request);
    String userVerify(UserVerifyRequest request);
}
