package ru.effectivemobile.social_network.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.effectivemobile.social_network.dto.RegistrationRequest;
import ru.effectivemobile.social_network.security.JwtProvider;
import ru.effectivemobile.social_network.security.JwtResponse;
import ru.effectivemobile.social_network.security.LoginRequest;
import ru.effectivemobile.social_network.security.RefreshTokenRequest;
import ru.effectivemobile.social_network.service.AuthService;

import javax.validation.Valid;

@Tag(name = "Авторизация и аутентификация пользователя")
@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthController {
    private final AuthService authService;
    private final JwtProvider tokenProvider;

    @Operation(summary = "Login")
    @PostMapping("login")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @Operation(summary = "Обновление токенов")
    @PostMapping("refresh")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        if (!tokenProvider.validateJwtToken(refreshToken.getRefreshToken())) {
            throw new RuntimeException();
        }
        return authService.refreshToken(refreshToken);
    }



    @Operation(summary = "Регистрация пользователя")
    @PostMapping("registration")
    public void addUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        authService.userRegistration(registrationRequest);
    }

}
