package ru.effectivemobile.social_network.ontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.effectivemobile.social_network.dao.PostRepository;
import ru.effectivemobile.social_network.dto.post.UpdatePostRequest;
import ru.effectivemobile.social_network.model.Post;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ru.effectivemobile.social_network.TestUtils.getPost;
import static ru.effectivemobile.social_network.TestUtils.getUpdatePostRequest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;
    @MockBean
    PostRepository postRepository;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @WithMockUser("spring")
    @Test
    public void getPostByIdShouldPassSuccess() throws Exception {
        Post post = getPost();
        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.of(post));
        mvc.perform(MockMvcRequestBuilders.get("/post/{id}", post.getId()))
                .andExpect(jsonPath("$.text").value(post.getText()))
                .andExpect(jsonPath("$.header").value(post.getHeader()));
    }
    @Test
    @WithMockUser("spring")
    public void updatePostByIdShouldPassSuccess() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Post post = getPost();
        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.of(post));
        UpdatePostRequest dto = getUpdatePostRequest();
        String givenDtoJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        Mockito.when(postRepository.save(Mockito.any())).thenReturn(post);
        mvc.perform(MockMvcRequestBuilders.put("/post/{id}", post.getId()).content(givenDtoJson))
                .andExpect(jsonPath("$.text").value(post.getText()))
                .andExpect(jsonPath("$.header").value(post.getHeader()));
    }

}
