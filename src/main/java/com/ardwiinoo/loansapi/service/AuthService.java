package com.ardwiinoo.loansapi.service;

import com.ardwiinoo.loansapi.model.dto.user.*;

public interface AuthService {
    UserDto userRegister(UserRegisterRequest request);
    UserTokenResponse userLogin(UserLoginRequest request);
    void userLogout(String refreshToken);
    UserTokenResponse userRenewToken(UserRenewTokenRequest request);
}
