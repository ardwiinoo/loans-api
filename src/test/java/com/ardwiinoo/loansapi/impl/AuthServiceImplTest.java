package com.ardwiinoo.loansapi.impl;

import com.ardwiinoo.loansapi.exception.AuthenticationError;
import com.ardwiinoo.loansapi.exception.InvariantError;
import com.ardwiinoo.loansapi.mapper.UserMapper;
import com.ardwiinoo.loansapi.model.dto.user.UserDto;
import com.ardwiinoo.loansapi.model.dto.user.UserLoginRequest;
import com.ardwiinoo.loansapi.model.dto.user.UserRegisterRequest;
import com.ardwiinoo.loansapi.model.dto.user.UserTokenResponse;
import com.ardwiinoo.loansapi.model.entity.Authentication;
import com.ardwiinoo.loansapi.model.entity.User;
import com.ardwiinoo.loansapi.repository.AuthenticationRepository;
import com.ardwiinoo.loansapi.repository.UserRepository;
import com.ardwiinoo.loansapi.service.JWTService;
import com.ardwiinoo.loansapi.service.impl.AuthServiceImpl;
import com.ardwiinoo.loansapi.util.ValidationUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationRepository authenticationRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ValidationUtil validationUtil;

    @Mock
    private JWTService jwtService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // TEST USER REGISTER
    @Test
    void userRegister_ShouldThrowInvariantError_WhenValidationFails() {
        UserRegisterRequest request = new UserRegisterRequest(
                "invalid-email",
                "password",
                "John",
                "Doe",
                "123456789"
        );

        Mockito.doThrow(new InvariantError("Validation failed")).when(validationUtil).validate(request);

        InvariantError exception = Assertions.assertThrows(InvariantError.class, () -> authService.userRegister(request));

        Assertions.assertEquals("Validation failed", exception.getMessage());
    }

    @Test
    void userRegister_ShouldThrowInvariantError_WhenEmailAlreadyUsed() {
        UserRegisterRequest request = new UserRegisterRequest(
                "test@example.com",
                "password",
                "John",
                "Doe",
                "123456789"
        );

        User existingUser = User.builder()
                .email("test@example.com")
                .password("hashedPassword")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        InvariantError exception = Assertions.assertThrows(InvariantError.class, () -> authService.userRegister(request));

        Assertions.assertEquals("Email already in used", exception.getMessage());
        Mockito.verify(userRepository).findByEmail(request.getEmail());
    }

    @Test
    void userRegister_ShouldRegisterNewUser_WhenRequestIsValid() {
        UserRegisterRequest request = new UserRegisterRequest(
                "test@example.com",
                "password",
                "John",
                "Doe",
                "123456789"
        );

        User savedUser = User.builder()
                .id(1L) // simulasi ID
                .email(request.getEmail())
                .password("hashedPassword")
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .isEnable(true)
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(savedUser);

        UserDto mappedUserDto = UserMapper.MAPPER.toUserDto(savedUser);
        UserDto result = authService.userRegister(request);

        Mockito.verify(userRepository).save(ArgumentMatchers.any(User.class));

        Assertions.assertNotNull(result);

        Assertions.assertEquals(mappedUserDto.getEmail(), result.getEmail());
        Assertions.assertEquals(mappedUserDto.getFirstName(), result.getFirstName());
        Assertions.assertEquals(mappedUserDto.getLastName(), result.getLastName());
        Assertions.assertEquals(mappedUserDto.isEnable(), result.isEnable());
    }

    // TEST USER LOGIN
    @Test
    void userLogin_ShouldThrowInvariantError_WhenValidationFails() {
        UserLoginRequest request = new UserLoginRequest(
                "",
                ""
        );

        Mockito.doThrow(new InvariantError("Validation failed")).when(validationUtil).validate(request);

        InvariantError exception = Assertions.assertThrows(InvariantError.class, () -> authService.userLogin(request));

        Assertions.assertEquals("Validation failed", exception.getMessage());
    }

    @Test
    void userLogin_ShouldThrowAuthenticationError_WhenInvalidEmailCredentials() {
        UserLoginRequest request = new UserLoginRequest(
                "test@gmail.com",
                "password"
        );

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        AuthenticationError exception = Assertions.assertThrows(AuthenticationError.class, () -> authService.userLogin(request));

        Assertions.assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void userLogin_ShouldThrowAuthenticationError_WhenInvalidPasswordCredentials() {
        UserLoginRequest request = new UserLoginRequest(
                "test@gmail.com",
                "password"
        );

        User existingUser = User.builder()
                .email("test@example.com")
                .password("hashedPassword")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("123456789")
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        Mockito.when(passwordEncoder.matches(request.getPassword(), existingUser.getPassword())).thenReturn(false);

        AuthenticationError exception = Assertions.assertThrows(AuthenticationError.class, () -> authService.userLogin(request));

        Assertions.assertEquals("Invalid credentials", exception.getMessage());
    }

    @Test
    void userLogin_ShouldThrowAuthenticationError_WhenUserNotEnable() {
        UserLoginRequest request = new UserLoginRequest(
                "test@gmail.com",
                "password"
        );

        User existingUser = User.builder()
                .email("test@example.com")
                .password("hashedPassword")
                .firstName("John")
                .lastName("Doe")
                .isEnable(false)
                .phoneNumber("123456789")
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        Mockito.when(passwordEncoder.matches(request.getPassword(), existingUser.getPassword())).thenReturn(true);

        AuthenticationError exception = Assertions.assertThrows(AuthenticationError.class, () -> authService.userLogin(request));

        Assertions.assertEquals("Please activate your account", exception.getMessage());
    }

    @Test
    void userLogin_ShouldLogin_WhenRequestIsValid() {
        UserLoginRequest request = new UserLoginRequest(
                "test@gmail.com",
                "password"
        );

        User existingUser = User.builder()
                .email("test@example.com")
                .password("hashedPassword")
                .firstName("John")
                .lastName("Doe")
                .isEnable(true)
                .phoneNumber("123456789")
                .build();

        Mockito.when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        Mockito.when(passwordEncoder.matches(request.getPassword(), existingUser.getPassword())).thenReturn(true);

        Mockito.when(jwtService.generateAccessToken(existingUser.getUsername())).thenReturn("accessToken");

        Mockito.when(jwtService.generateRefreshToken(existingUser.getUsername())).thenReturn("refreshToken");

        Authentication authentication = Authentication.builder()
                .refreshToken("refreshToken")
                .build();

        Mockito.when(authenticationRepository.save(authentication)).thenReturn(authentication);

        UserTokenResponse result = authService.userLogin(request);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("accessToken", result.getAccessToken());
        Assertions.assertEquals("refreshToken", result.getRefreshToken());
    }
}
