package ru.effectivemobile.social_network.exception;

import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;

public class UserExistsException extends AuthenticationException {
    public UserExistsException(@Nullable String message) {
        super(message);
    }
}
