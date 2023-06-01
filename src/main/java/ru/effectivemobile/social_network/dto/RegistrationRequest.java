package ru.effectivemobile.social_network.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Информация для регистрации пользователя пользователю")
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class RegistrationRequest {
    @NotBlank
    @Schema(name = "login", description = "Логин пользователя")
    String login;
    @NotBlank
    @Schema(name = "password", description = "Пароль пользователя")
    String password;
    @NotBlank
    @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "Не правильный формат email")
    @Schema(name = "email", description = "Электронная почта пользователя")
    String email;
}
