package ru.effectivemobile.social_network.service.post;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.effectivemobile.social_network.dto.post.CreatePostRequest;
import ru.effectivemobile.social_network.dto.post.ShowPostResponse;
import ru.effectivemobile.social_network.dto.post.UpdatePostRequest;

import java.util.List;

@Service
public interface PostService {
    void createPost(CreatePostRequest createPostRequest, MultipartFile multipartFile);

    ShowPostResponse getPost(long id);

    ShowPostResponse updatePost(long id, UpdatePostRequest updatePostRequest);

    void deletePost(long id);

    List<ShowPostResponse> getActivity(PageRequest of, long userId);
}
