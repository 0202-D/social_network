package ru.effectivemobile.social_network.service.user;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.effectivemobile.social_network.dao.FriendRepository;
import ru.effectivemobile.social_network.dao.UserRepository;
import ru.effectivemobile.social_network.exception.IncorrectDataException;
import ru.effectivemobile.social_network.exception.NotFoundException;
import ru.effectivemobile.social_network.model.Friend;
import ru.effectivemobile.social_network.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.effectivemobile.social_network.TestUtils.getUser;
import static ru.effectivemobile.social_network.model.ClaimAsFriendStatus.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendRepository friendRepository;


    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void testCreateFriendClaim() {
        // Set up authentication context
        User firstUser = getUser();
        firstUser.setId(1L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(firstUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Mock user repository
        User secondUser = getUser();
        secondUser.setId(2L);
        when(userRepository.findById(2L)).thenReturn(Optional.of(secondUser));

        // Execute service method
        userService.createFriendClaim(2L);

        // Verify friend creation
        ArgumentCaptor<Friend> friendArgumentCaptor = ArgumentCaptor.forClass(Friend.class);
        verify(friendRepository).save(friendArgumentCaptor.capture());
        Friend friend = friendArgumentCaptor.getValue();
        assertEquals(firstUser, friend.getFirstUser());
        assertEquals(secondUser, friend.getSecondUser());
        assertEquals(NEW, friend.getStatus());
    }
    @Test
    public void testConfirmFriend() {
            // Set up authentication context
            User currentUser = getUser();
            currentUser.setId(1L);
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Mock friend repository
            User secondUser = getUser();
            secondUser.setId(2L);
            Friend friend = new Friend();
            friend.setFirstUser(secondUser);
            friend.setSecondUser(currentUser);
            friend.setStatus(NEW);
            when(friendRepository.findBySecondUserIdAndFirstUserId(1L, 2L)).thenReturn(Optional.of(friend));

            // Execute service method
            userService.confirmFriend(2L);

            // Verify friend confirmation
            assertEquals(CONFIRMED, friend.getStatus());
            ArgumentCaptor<Friend> friendArgumentCaptor = ArgumentCaptor.forClass(Friend.class);
            verify(friendRepository, Mockito.times(2)).save(friendArgumentCaptor.capture());
            List<Friend> savedFriends = friendArgumentCaptor.getAllValues();
            assertEquals(2, savedFriends.size());
            assertEquals(currentUser, savedFriends.get(1).getFirstUser());
            assertEquals(secondUser, savedFriends.get(1).getSecondUser());
            assertEquals(CONFIRMED, savedFriends.get(0).getStatus());
            assertEquals(secondUser, savedFriends.get(0).getFirstUser());
            assertEquals(currentUser, savedFriends.get(0).getSecondUser());
            assertEquals(CONFIRMED, savedFriends.get(1).getStatus());

        }
    @Test
    public void testRejectFriend() {
        User currentUser = new User();
        currentUser.setId(1L);
        Friend friend = new Friend();
        friend.setId(1L);
        friend.setStatus(NEW);
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // check that the friend request exists and has the status NEW

        when(friendRepository.findBySecondUserIdAndFirstUserId(currentUser.getId(), friend.getId()))
                .thenReturn(Optional.of(friend));

        userService.rejectFriend(friend.getId());

        Assertions.assertEquals(REJECTED, friend.getStatus());
        verify(friendRepository).save(friend);

        // We check that the method throws a NotFoundException exception if the friend request is not found
        long nonExistingFriendId = 2L;

        when(friendRepository.findBySecondUserIdAndFirstUserId(currentUser.getId(), nonExistingFriendId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.rejectFriend(nonExistingFriendId);
        });

        // Check that the method throws an Incorrect Data Exception if the request status is not NEW
        Friend approvedFriend = new Friend();
        approvedFriend.setId(1L);
        approvedFriend.setStatus(CONFIRMED);
        when(friendRepository.findBySecondUserIdAndFirstUserId(currentUser.getId(), approvedFriend.getId()))
                .thenReturn(Optional.of(approvedFriend));

        Assertions.assertThrows(IncorrectDataException.class, () -> {
            userService.rejectFriend(approvedFriend.getId());
        });
    }

}



