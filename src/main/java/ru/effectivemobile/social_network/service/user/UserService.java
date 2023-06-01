package ru.effectivemobile.social_network.service.user;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createFriendClaim(long userId);

    void confirmFriend(long userId);

    void rejectFriend(long userId);

    void removeFriend(long userId);
}
