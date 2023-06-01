package ru.effectivemobile.social_network.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.effectivemobile.social_network.dao.FriendRepository;
import ru.effectivemobile.social_network.dao.UserRepository;
import ru.effectivemobile.social_network.exception.IncorrectDataException;
import ru.effectivemobile.social_network.exception.NotFoundException;
import ru.effectivemobile.social_network.model.Friend;
import ru.effectivemobile.social_network.model.User;

import static ru.effectivemobile.social_network.model.ClaimAsFriendStatus.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    @Transactional
    public void createFriendClaim(long userId) {
        User firstUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User secondUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        Friend friend = Friend.builder()
                .firstUser(firstUser)
                .secondUser(secondUser)
                .status(NEW)
                .build();
        friendRepository.save(friend);

    }
// firstUser является подписчиком
    @Override
    @Transactional
    public void confirmFriend(long userId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Friend friend = friendRepository.findBySecondUserIdAndFirstUserId(currentUser.getId(),userId)
                .orElseThrow(() -> new NotFoundException("У этого пользователя нет этой заявки в друзья "));
        if (friend.getStatus() != NEW) {
            throw new IncorrectDataException("У Вас нет новых заявок в друзью");
        }
        friend.setStatus(CONFIRMED);

        User secondUser = friend.getFirstUser();
        Friend nextFriend = Friend.builder()
                .firstUser(currentUser)
                .secondUser(secondUser)
                .status(CONFIRMED)
                .build();
        friendRepository.save(friend);
        friendRepository.save(nextFriend);
    }

    @Override
    public void rejectFriend(long userId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Friend friend = friendRepository.findBySecondUserIdAndFirstUserId(currentUser.getId(),userId)
                .orElseThrow(() -> new NotFoundException("У этого пользователя нет этой заявки в друзья "));
        if (friend.getStatus() != NEW) {
            throw new IncorrectDataException("У Вас нет новых заявок в друзью");
        }
        friend.setStatus(REJECTED);
        friendRepository.save(friend);
    }

    @Override
    @Transactional
    public void removeFriend(long userId) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Friend secondFriend = friendRepository.findBySecondUserIdAndFirstUserId(currentUser.getId(),userId)
                .orElseThrow(() -> new NotFoundException("У этого пользователя нет этой заявки в друзья "));
       Friend friend = friendRepository.findBySecondUserIdAndFirstUserId(userId, currentUser.getId())
               .orElseThrow(() -> new NotFoundException("У этого пользователя нет этой заявки в друзья "));
       friendRepository.delete(friend);
       secondFriend.setStatus(REJECTED);
       friendRepository.save(secondFriend);
    }
}

