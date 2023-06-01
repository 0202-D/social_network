package ru.effectivemobile.social_network.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.effectivemobile.social_network.dao.FriendRepository;
import ru.effectivemobile.social_network.dao.UserRepository;
import ru.effectivemobile.social_network.model.Friend;
import ru.effectivemobile.social_network.model.User;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static ru.effectivemobile.social_network.TestUtils.getUser;
import static ru.effectivemobile.social_network.model.ClaimAsFriendStatus.NEW;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendRepository friendRepository;

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
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(secondUser));

        // Execute service method
        userService.createFriendClaim(2L);

        // Verify friend creation
        ArgumentCaptor<Friend> friendArgumentCaptor = ArgumentCaptor.forClass(Friend.class);
        Mockito.verify(friendRepository).save(friendArgumentCaptor.capture());
        Friend friend = friendArgumentCaptor.getValue();
        assertEquals(firstUser, friend.getFirstUser());
        assertEquals(secondUser, friend.getSecondUser());
        assertEquals(NEW, friend.getStatus());
    }
}


