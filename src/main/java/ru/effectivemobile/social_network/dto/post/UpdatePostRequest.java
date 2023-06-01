package ru.effectivemobile.social_network.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class UpdatePostRequest {
    @Schema(name = "text", description = "Текст поста")
    String text;
    @Schema(name = "header", description = "Заголовок поста")
    String header;
}
