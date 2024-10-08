package com.ardwiinoo.loansapi.service.impl;

import com.ardwiinoo.loansapi.exception.AuthenticationError;
import com.ardwiinoo.loansapi.exception.InvariantError;
import com.ardwiinoo.loansapi.exception.NotFoundError;
import com.ardwiinoo.loansapi.mapper.UserMapper;
import com.ardwiinoo.loansapi.model.dto.user.*;
import com.ardwiinoo.loansapi.model.entity.Authentication;
import com.ardwiinoo.loansapi.model.entity.User;
import com.ardwiinoo.loansapi.model.enums.UserRole;
import com.ardwiinoo.loansapi.repository.AuthenticationRepository;
import com.ardwiinoo.loansapi.repository.UserRepository;
import com.ardwiinoo.loansapi.service.AuthService;
import com.ardwiinoo.loansapi.service.JWTService;
import com.ardwiinoo.loansapi.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ValidationUtil validationUtil;

    @Autowired
    private JWTService jwtService;

    @Override
    @Transactional
    public UserDto userRegister(UserRegisterRequest request) {
        validationUtil.validate(request);

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
            throw new InvariantError("Email already in used");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User saveUser = User.builder()
                .email(request.getEmail())
                .isEnable(true)
                .userRole(UserRole.USER)
                .password(hashedPassword)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .build();

        userRepository.save(saveUser);

        UserDto userDto =  UserMapper.MAPPER.toUserDto(saveUser);

        log.info("User registered: {}", userDto);

        return userDto;
    }

    @Override
    public UserTokenResponse userLogin(UserLoginRequest request) {
        validationUtil.validate(request);

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new AuthenticationError("Invalid credentials")
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthenticationError("Invalid credentials");
        }

        if (!user.isEnable()) {
            throw new AuthenticationError("Please activate your account");
        }

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        Authentication authentication = Authentication.builder()
                .refreshToken(refreshToken)
                .build();

        authenticationRepository.save(authentication);

        return new UserTokenResponse(
                accessToken, refreshToken
        );
    }

    @Override
    public void userLogout(UserRefreshTokenRequest request) {
        validationUtil.validate(request);

        Authentication authentication = authenticationRepository.findByRefreshToken(request.getRefreshToken()).orElseThrow(
                () -> new InvariantError("Invalid refresh token")
        );

        authenticationRepository.delete(authentication);
    }

    @Override
    public UserTokenResponse userRenewToken(UserRefreshTokenRequest request) {
        validationUtil.validate(request);

        Authentication authentication = authenticationRepository.findByRefreshToken(request.getRefreshToken()).orElseThrow(
                () -> new InvariantError("Invalid refresh token")
        );

        if (!jwtService.validateRefreshToken(authentication.getRefreshToken())) {
            throw new InvariantError("Token is expired");
        }

        String username = jwtService.extractUsernameFromRefreshToken(authentication.getRefreshToken());

        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new NotFoundError("User not found")
        );

        String accessToken = jwtService.generateAccessToken(user.getUsername());

        return new UserTokenResponse(
                accessToken, request.getRefreshToken()
        );
    }
}
