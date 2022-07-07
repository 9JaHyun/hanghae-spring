package com.blogservice.controller;

import com.blogservice.controller.dto.PostCreateRequestDto;
import com.blogservice.controller.dto.PostResponseDto;
import com.blogservice.controller.dto.PostUpdateRequestDto;
import com.blogservice.service.PostService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        PostResponseDto response = postService.posting(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
              .contentType(MediaType.APPLICATION_JSON)
              .body(response);
    }

    // 서비스로 내려보내자.
    @PutMapping("/posts")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostUpdateRequestDto dto) {
        PostResponseDto postResponseDto = postService.updatePost(dto);
        if (postResponseDto != null) {
            return ResponseEntity.status(HttpStatus.OK)
                  .contentType(MediaType.APPLICATION_JSON)
                  .body(postResponseDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(null);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> findAllPosts(
          @RequestParam(name = "s", defaultValue = "id") String sort) {

        List<PostResponseDto> posts = postService.findAll(sort);

        if (posts.size() == 0) {
            return ResponseEntity
                  .status(HttpStatus.NO_CONTENT)
                  .body(null);
        }
        return ResponseEntity
              .status(HttpStatus.OK)
              .contentType(MediaType.APPLICATION_JSON)
              .body(posts);
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponseDto> findPostById(@RequestParam Long id) {
        PostResponseDto post = postService.findById(id);

        return ResponseEntity
              .status(HttpStatus.OK)
              .contentType(MediaType.APPLICATION_JSON)
              .body(post);
    }

    @DeleteMapping("/posts/")
    public ResponseEntity<String> deletePost(@RequestBody Long id) {
        postService.delete(id);

        return ResponseEntity
              .status(HttpStatus.NO_CONTENT)
              .body("Delete Done");
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
