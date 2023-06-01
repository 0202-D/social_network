package ru.effectivemobile.social_network.service.user;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.effectivemobile.social_network.dao.FriendRepository;
import ru.effectivemobile.social_network.dao.UserRepository;
import ru.effectivemobile.social_network.model.Friend;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
public class UserServiceImplTest {

    // Добавить тесты

}
