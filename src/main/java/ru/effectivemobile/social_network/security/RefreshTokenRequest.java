package ru.effectivemobile.social_network.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@ToString
public class RefreshTokenRequest {
    private String refreshToken;
}
