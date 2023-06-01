package ru.effectivemobile.social_network.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

//401
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, value = HttpStatus.UNAUTHORIZED)
public class ErrorUnauthorized extends AuthenticationException {
    public ErrorUnauthorized(String msg) {
        super(msg);
    }
}
