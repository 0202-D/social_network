package ru.effectivemobile.social_network.exception;

import org.springframework.http.HttpStatus;

/**
 * возвращаемые коды ошибок на фронт
 */
public enum ErrorCodes {

    ERR_INVALID_LOGIN(HttpStatus.UNAUTHORIZED),
    ERR_UNEXPECTED(HttpStatus.INTERNAL_SERVER_ERROR),
    ERR_INVALID_REFRESH(HttpStatus.UNAUTHORIZED),
    ERR_NOT_FOUND(HttpStatus.NOT_FOUND),

    ERR_NO_RIGHTS(HttpStatus.FORBIDDEN),

    ERR_USER_EXISTS(HttpStatus.UNAUTHORIZED),
    ERR_INCORRECT_DATA(HttpStatus.BAD_REQUEST),

    ERR_PERMISSION_DENIED(HttpStatus.FORBIDDEN);

    private final HttpStatus httpStatus;

    ErrorCodes(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
