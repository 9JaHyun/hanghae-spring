package com.blogservice.controller;

import com.blogservice.controller.dto.PostCreateRequestDto;
import com.blogservice.controller.dto.PostResponseDto;
import com.blogservice.controller.dto.PostUpdateRequestDto;
import com.blogservice.domain.Post;
import com.blogservice.domain.PostEntity;
import com.blogservice.service.PostService;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String welcome() {
        return "hello";
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> posting(@RequestBody PostCreateRequestDto dto) {
        System.out.println(dto);
        PostResponseDto response = postService.posting(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
              .body(response);
    }

    // 서비스로 내려보내자.
    @PutMapping("/posts")
    public ResponseEntity<Post> updatePost(@RequestBody PostUpdateRequestDto dto) {
        PostEntity findEntity = postService.findById(dto.getId());
        if (findEntity.getPassword().equals(dto.getPassword())) {
            findEntity.setTitle(dto.getTitle());
            findEntity.setContent(dto.getContent());
            postService.updatePost(findEntity);

            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            Post post = mapper.map(findEntity, Post.class);

            return ResponseEntity.status(HttpStatus.OK)
                  .body(post);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(null);
    }

    @GetMapping("/posts")
        public ResponseEntity<List<Post>> posts(@RequestParam(name = "sort", defaultValue = "id") String sort) {
            List<PostEntity> postEntities = null;
            if (sort.equals("id")) {
                postEntities = postService.findAll();
            }

            if (sort.equals("createdAt")) {
                postEntities = postService.findAll();
            }

            ModelMapper mapper = new ModelMapper();
            List<Post> posts = new ArrayList<>();

            postEntities.forEach(p -> posts.add(mapper.map(p, Post.class)));

            return ResponseEntity
              .status(HttpStatus.OK)
              .body(posts);
    }

    @GetMapping("/post")
    public ResponseEntity<Post> post(@RequestParam Long id) {
        PostEntity postEntity = postService.findById(id);

        ModelMapper mapper = new ModelMapper();
        Post post = mapper.map(postEntity, Post.class);

        return ResponseEntity
              .status(HttpStatus.OK)
              .body(post);
    }

    @DeleteMapping("/posts/")
    public ResponseEntity<String> deletePost(@RequestBody Long id) {
        postService.delete(id);

        return ResponseEntity
              .status(HttpStatus.OK)
              .body("Delete Done");
    }

}
