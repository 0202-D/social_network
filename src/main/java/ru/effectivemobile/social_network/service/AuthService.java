package ru.effectivemobile.social_network.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.social_network.dao.RoleRepository;
import ru.effectivemobile.social_network.dao.TokenRepository;
import ru.effectivemobile.social_network.dao.UserRepository;
import ru.effectivemobile.social_network.dto.RegistrationRequest;
import ru.effectivemobile.social_network.exception.*;
import ru.effectivemobile.social_network.model.Role;
import ru.effectivemobile.social_network.model.Token;
import ru.effectivemobile.social_network.model.User;
import ru.effectivemobile.social_network.security.JwtProvider;
import ru.effectivemobile.social_network.security.JwtResponse;
import ru.effectivemobile.social_network.security.LoginRequest;
import ru.effectivemobile.social_network.security.RefreshTokenRequest;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder encoder;

    @Transactional
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        String notNullMessage = "Логин и пароль не могут ровняться null";
        String userNotFoundMessage = "Пользователь с логином %s не найден";
        String wrongPasswordMessage = "Введен неверный пароль";

        if (loginRequest.getLogin() == null || loginRequest.getPassword() == null) {
            LOGGER.error(notNullMessage);
            throw new InvalidLoginException(notNullMessage);
        }
        User user = userRepository.findUserByLogin(loginRequest.getLogin());

        if (user == null) {
            LOGGER.error(String.format(userNotFoundMessage, loginRequest.getLogin()));
            throw new InvalidLoginException(String.format(userNotFoundMessage, loginRequest.getLogin()));
        }

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            LOGGER.error(wrongPasswordMessage);
            throw new InvalidLoginException(wrongPasswordMessage);
        }
        //get token
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
        String refreshJwtToken = jwtProvider.generateRefreshJwtToken(user);

        Token newToken = Token.builder()
                .user(user)
                .refreshToken(refreshJwtToken)
                .createDate(Instant.now())
                .disabled(false)
                .deleted(false)
                .build();

        tokenRepository.save(newToken);

        return new JwtResponse(accessJwtToken, refreshJwtToken);

    }

    @Transactional
    public JwtResponse refreshToken(RefreshTokenRequest refreshToken) {
        Token token = tokenRepository.findTokenByRefreshToken(refreshToken.getRefreshToken());
        if (token == null || token.isDisabled()) {
            throw new InvalidTokenException();
        }
        token.setDisabled(true);
        tokenRepository.save(token);

        User user = userRepository.findUserById(token.getUser().getId());
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
        String refreshJwtToken = jwtProvider.generateRefreshJwtToken(user);

        Token newToken = Token.builder()
                .user(user)
                .refreshToken(refreshJwtToken)
                .createDate(Instant.now())
                .disabled(false)
                .deleted(false)
                .build();
        tokenRepository.save(newToken);
        return new JwtResponse(accessJwtToken, refreshJwtToken);
    }


    @Transactional
    public void userRegistration(@Valid RegistrationRequest registrationRequest) {
        User foundedUser = userRepository.findUserByLogin(registrationRequest.getLogin());
        if (foundedUser != null) {
            throw new UserExistsException("Пользователь с данным логином уже существует");
        }
        if (userRepository.existsByEmail(registrationRequest.getEmail())){
            throw new IncorrectDataException("Такой email уже существует");
        }
        User user = User.builder().
                login(registrationRequest.getLogin())
                .password(encoder.encode(registrationRequest.getPassword()))
                .email(registrationRequest.getEmail()).build();
        Role role = roleRepository.findRoleByName("ROLE_GUEST")
                .orElseThrow(()->new NotFoundException("Такой роли не существует"));
        user.setUserRoles(Set.of(role));
        userRepository.save(user);
    }


}
