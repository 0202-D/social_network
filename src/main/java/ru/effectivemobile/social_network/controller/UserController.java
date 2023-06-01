package ru.effectivemobile.social_network.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.effectivemobile.social_network.service.user.UserService;

@RequiredArgsConstructor
@Tag(name = "Информация по пользователю")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Operation(summary = "создание заявки в друзья")
    @PostMapping("/claim/{userId}")
    public void createFriendClaim(@Parameter(description = "Идентификатор пользователя", required = true)
                                      @PathVariable long userId) {
         userService.createFriendClaim(userId);
    }
    @Operation(summary = "подтверждение дружбы")
    @PutMapping("/claim/confirm/{userId}")
    public void confirmFriend(@Parameter(description = "Идентификатор пользователя", required = true)
    @PathVariable long userId){
        userService.confirmFriend(userId);
    }
    @Operation(summary = "отклонение дружбы")
    @PutMapping("/claim/reject/{userId}")
    public void rejectFriend(@Parameter(description = "Идентификатор пользователя", required = true)
                              @PathVariable long userId){
        userService.rejectFriend(userId);
    }
    @Operation(summary = "удаление из друзей")
    @PutMapping("/claim/reove/{userId}")
    public void removeFriend(@Parameter(description = "Идентификатор пользователя", required = true)
                             @PathVariable long userId){
        userService.removeFriend(userId);
    }
}
