package ru.effectivemobile.social_network.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.effectivemobile.social_network.service.AuthService;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.effectivemobile.social_network.exception.ErrorCodes.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private static final Map<Class<? extends RuntimeException>, ErrorCodes> errors = Map.of(
            InvalidLoginException.class, ERR_INVALID_LOGIN,
            InvalidTokenException.class, ERR_INVALID_REFRESH,
            NotFoundException.class, ERR_NOT_FOUND,
            IncorrectDataException.class, ERR_INCORRECT_DATA
    );

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        ErrorCodes errorCode = Optional.ofNullable(errors.get(e.getClass())).orElse(ERR_UNEXPECTED);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder().code(errorCode).message(e.getMessage()).build());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    public record ValidationErrorResponse(List<Violation> violations) {
    }

    public record Violation(String fieldName, String message) {
    }

    @Getter
    @Setter
    @Builder
    private static class ErrorResponse {
        private ErrorCodes code;
        private String message;
    }
}
