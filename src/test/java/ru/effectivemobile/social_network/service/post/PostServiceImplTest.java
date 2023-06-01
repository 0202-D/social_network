package ru.effectivemobile.social_network.service.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.effectivemobile.social_network.dao.PostRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static ru.effectivemobile.social_network.TestUtils.*;

@ExtendWith({MockitoExtension.class})
 class PostServiceImplTest {

    @InjectMocks
    @Spy
    PostServiceImpl subj;
     @Mock
     PostRepository postRepository;

     @Test
    void getPostTest(){
         var post = getPost();
         doReturn(Optional.of(post)).when(postRepository).findById(anyLong());
         var expected = getShowResponse();
         var actual= subj.getPost(1);
         assertEquals(expected,actual);
     }


}
