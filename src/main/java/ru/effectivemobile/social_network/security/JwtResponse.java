package ru.effectivemobile.social_network.security;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
@Setter
@ToString
public class JwtResponse {

    private String accessToken;
    private String refreshToken;

}
