package com.blogservice.service;

import com.blogservice.controller.dto.PostCreateRequestDto;
import com.blogservice.controller.dto.PostResponseDto;
import com.blogservice.domain.PostEntity;
import com.blogservice.repository.PostRepository;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto posting(PostCreateRequestDto dto) {
        ModelMapper mapper = new ModelMapper();
        PostEntity entity = mapper.map(dto, PostEntity.class);

        postRepository.save(entity);

        return mapper.map(entity, PostResponseDto.class);
    }

    // 방법2. 전체 가져와서 Service Layer에서 정렬하기
//    public List<PostEntity> findPostSortByCreatedAt() {
//        return postRepository.findAll();
//    }

    public void updatePost(PostEntity post) {
        postRepository.save(post);
    }

    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    public PostEntity findById(Long id) {
        return postRepository
              .findById(id)
              .orElseThrow(() -> new IllegalArgumentException("적절하지 않은 id 입니다."));
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
