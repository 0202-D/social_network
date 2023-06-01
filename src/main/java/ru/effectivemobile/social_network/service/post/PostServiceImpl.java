package ru.effectivemobile.social_network.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.effectivemobile.social_network.dao.FriendRepository;
import ru.effectivemobile.social_network.dao.PostRepository;
import ru.effectivemobile.social_network.dto.post.CreatePostRequest;
import ru.effectivemobile.social_network.dto.post.ShowPostResponse;
import ru.effectivemobile.social_network.dto.post.UpdatePostRequest;
import ru.effectivemobile.social_network.exception.IncorrectDataException;
import ru.effectivemobile.social_network.exception.NotFoundException;
import ru.effectivemobile.social_network.model.Friend;
import ru.effectivemobile.social_network.model.Post;
import ru.effectivemobile.social_network.model.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final FriendRepository friendRepository;


    @Override
    @Transactional
    public void createPost(CreatePostRequest createPostRequest, MultipartFile multipartFile) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = Post.builder()
                .header(createPostRequest.getHeader())
                .text(createPostRequest.getText())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        try {
            post.setData(multipartFile.getBytes());
        } catch (IOException e) {
            throw new IncorrectDataException("Ошибка при загрузке изображения");
        }
        postRepository.save(post);
    }

    @Override
    public ShowPostResponse getPost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Поста с таким идентификатором не найдено"));
        return ShowPostResponse.builder()
                .text(post.getText())
                .header(post.getHeader())
                .data(post.getData())
                .userId(post.getUser().getId())
                .createdAt(post.getCreatedAt())
                .build();
    }

    @Override
    public ShowPostResponse updatePost(long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Поста с таким id не существует"));
        post.setText(updatePostRequest.getText());
        post.setHeader(updatePostRequest.getHeader());
        postRepository.save(post);
        return ShowPostResponse.builder()
                .text(post.getText())
                .header(post.getHeader())
                .data(post.getData())
                .userId(post.getUser().getId())
                .build();
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<ShowPostResponse> getActivity(PageRequest of, long userId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Friend friend = friendRepository.findBySecondUserIdAndFirstUserId(userId, user.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь не подписан на пользователя с id" + userId));
        List<Post> posts = postRepository.findAllByUserId(userId, of);

        return posts.stream().map(el -> ShowPostResponse.builder()
                .text(el.getText())
                .header(el.getHeader())
                .createdAt(el.getCreatedAt())
                .data(el.getData())
                .userId(el.getUser().getId())
                .build()
        ).collect(Collectors.toList());
    }
}
