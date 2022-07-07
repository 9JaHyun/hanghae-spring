package com.blogservice.service;

import com.blogservice.controller.dto.PostCreateRequestDto;
import com.blogservice.controller.dto.PostResponseDto;
import com.blogservice.controller.dto.PostUpdateRequestDto;
import com.blogservice.domain.PostEntity;
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
        PostEntity postEntity = mapper.map(dto, PostEntity.class);
        postRepository.save(postEntity);

        return mapper.map(postEntity, PostResponseDto.class);
    }

    @Transactional
    public PostResponseDto updatePost(PostUpdateRequestDto dto) {
        PostEntity entity = postRepository.findByIdAndPassword(dto.getId(), dto.getPassword());
        if (entity != null) {
            return mapper.map(entity, PostResponseDto.class);
        } else {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
    }

    public List<PostResponseDto> findAll(String sort) {
        if (sort.contains("_desc")) {
            String keyword = sort.substring(0, sort.indexOf("_desc"));
            System.out.println(postRepository.findAll(Sort.by(Order.desc(keyword))));
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
              .orElseThrow(() -> new IllegalArgumentException("적절하지 않은 id 입니다."));

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(postEntity, PostResponseDto.class);
    }

    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
