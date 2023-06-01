package ru.effectivemobile.social_network.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class ShowPostResponse {
    @Schema(name = "text", description = "Текст поста")
    String text;

    @Schema(name = "header", description = "Заголовок поста")
    String header;
    // Пока просто возвращаем картинку как массив байт , в будущем сделать хранения файла в памяти , а в базе хранить ссылку
    private byte [] data;

    @Schema(name = "userId", description = "Идентификатор пользователя")
    long userId;

    @Schema(name = "createdAt", description = "Время создания")
    LocalDateTime createdAt;

}
