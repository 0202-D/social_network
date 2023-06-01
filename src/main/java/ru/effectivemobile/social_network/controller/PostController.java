package ru.effectivemobile.social_network.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.effectivemobile.social_network.dto.post.CreatePostRequest;
import ru.effectivemobile.social_network.dto.post.ShowPostResponse;
import ru.effectivemobile.social_network.dto.post.UpdatePostRequest;
import ru.effectivemobile.social_network.model.Post;
import ru.effectivemobile.social_network.service.post.PostService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RequiredArgsConstructor
@Tag(name = "Информация по постам")
@RestController
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Operation(summary = "Создание поста")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createPost(CreatePostRequest createPostRequest,
                           @RequestPart(name = "postcard_image") MultipartFile multipartFile) {
        postService.createPost(createPostRequest, multipartFile);
    }

    @Operation(summary = "Просмотр поста")
    @GetMapping("/{id}")
    public ShowPostResponse showPost(@Parameter(description = "Идентификатор поста", required = true) @PathVariable long id) {
        return postService.getPost(id);
    }

    @Operation(summary = "Редактирование поста")
    @PutMapping("/{id}")
    public ShowPostResponse updatePost(@Parameter(description = "Идентификатор поста", required = true) @PathVariable long id,
                                       UpdatePostRequest updatePostRequest) {
        return postService.updatePost(id, updatePostRequest);
    }

    @Operation(summary = "Удаление поста")
    @DeleteMapping("/{id}")
    public void deletePost(@Parameter(description = "Идентификатор поста", required = true) @PathVariable long id) {
        postService.deletePost(id);
    }

    @Operation(summary = "Просмотр активностей")
    @GetMapping
    public List<ShowPostResponse> activity(@Parameter(name = "pages", description = "От 0")
                                           @RequestParam(defaultValue = "0") @PositiveOrZero int pages,
                                           @Parameter(name = "elements", description = "От 1 до 200")
                                           @RequestParam(defaultValue = "8") @Min(value = 1) @Max(value = 200) int elements,long userId){
        return postService.getActivity(PageRequest.of(pages, elements),userId);
    }
}
