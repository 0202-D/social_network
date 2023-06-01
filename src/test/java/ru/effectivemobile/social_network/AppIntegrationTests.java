package ru.effectivemobile.social_network;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.effectivemobile.social_network.security.LoginRequest;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AppIntegrationTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final String LOGIN_PATH = "/authentication/login";
    private final String LOGIN = "login1";
    private final String BAD_LOGIN = "admin";
    private final String PASSWORD = "password1";
    private final String ANOTHER_PATH = "/post";

    @Test
    void userIsUnauthorized() throws Exception {
        mvc.perform(get(ANOTHER_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginUserUnauthenticated() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().login(BAD_LOGIN).password(PASSWORD).build();
        mvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loginUserAuthenticated() throws Exception {
        LoginRequest loginRequest = LoginRequest.builder().login(LOGIN).password(PASSWORD).build();
        mvc.perform(post(LOGIN_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }

}
