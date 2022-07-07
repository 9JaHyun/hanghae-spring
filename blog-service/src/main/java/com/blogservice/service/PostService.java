package com.blogservice.service;

import com.blogservice.controller.dto.PostCreateRequestDto;
import com.blogservice.controller.dto.PostDeleteRequestDto;
import com.blogservice.controller.dto.PostResponseDto;
import com.blogservice.controller.dto.PostUpdateRequestDto;
import com.blogservice.domain.PostEntity;
import com.blogservice.exception.IllegalPostPasswordException;
import com.blogservice.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public PostService(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Transactional
    public PostResponseDto posting(PostCreateRequestDto dto) {
        System.out.println(dto);
        PostEntity postEntity = mapper.map(dto, PostEntity.class);
        System.out.println(postEntity);
        postRepository.save(postEntity);

        return mapper.map(postEntity, PostResponseDto.class);
    }

    @Transactional
    public PostResponseDto updatePost(PostUpdateRequestDto dto) {
        PostEntity entity = postRepository.findByIdAndPassword(dto.getId(), dto.getPassword());
        if (entity != null) {
            return mapper.map(entity, PostResponseDto.class);
        } else {
            throw new IllegalPostPasswordException("패스워드가 일치하지 않습니다.");
        }
    }

    public List<PostResponseDto> findAll(String sort) {
        if (sort.contains("_desc")) {
            String keyword = sort.substring(0, sort.indexOf("_desc"));
            return postRepository.findAll(Sort.by(Order.desc(keyword))).stream()
                  .map(entity -> mapper.map(entity, PostResponseDto.class))
                  .collect(Collectors.toList());
        }
        return postRepository.findAll(Sort.by(sort)).stream()
              .map(entity -> mapper.map(entity, PostResponseDto.class))
              .collect(Collectors.toList());
    }

    public PostResponseDto findById(Long id) {
        PostEntity postEntity = postRepository
              .findById(id)
              .orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다."));

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(postEntity, PostResponseDto.class);
    }

    @Transactional
    public void delete(PostDeleteRequestDto dto) {
        PostEntity entity = postRepository.findByIdAndPassword(dto.getId(), dto.getPassword());
        if (entity != null) {
            postRepository.deleteById(dto.getId());
        } else {
            throw new IllegalPostPasswordException("패스워드가 일치하지 않습니다.");
        }
    }
}
