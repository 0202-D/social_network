package ru.effectivemobile.social_network;

import ru.effectivemobile.social_network.dto.post.ShowPostResponse;
import ru.effectivemobile.social_network.dto.post.UpdatePostRequest;
import ru.effectivemobile.social_network.model.Post;
import ru.effectivemobile.social_network.model.User;

import java.time.LocalDateTime;
import java.time.Month;

public class TestUtils {
    public static User getUser() {
        return User.builder()
                .id(1L).
                login("Ivan")
                .email("ivan@gmail.com")
                .build();
    }

    public static Post getPost() {
        return Post.builder().id(1L)
                .text("ABC")
                .header("DEF")
                .createdAt(LocalDateTime.of(2000, Month.APRIL, 15, 12, 30))
                .data(null)
                .user(getUser()).build();
    }

    public static ShowPostResponse getShowResponse() {
        return ShowPostResponse.builder()
                .text("ABC")
                .header("DEF")
                .createdAt(LocalDateTime.of(2000, Month.APRIL, 15, 12, 30))
                .data(null)
                .userId(1l).build();
    }
    public static UpdatePostRequest getUpdatePostRequest(){
        return UpdatePostRequest.builder()
                .text("New Text")
                .header("New header").build();
    }
}
