package com.blogservice.service;

import com.blogservice.controller.dto.PostCreateRequestDto;
import com.blogservice.domain.PostEntity;
import com.blogservice.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @DisplayName(value = "게시글 포스팅 테스트")
    @Test
    void postingTest(){
        PostCreateRequestDto dto = new PostCreateRequestDto();
        dto.setTitle("제목1");
        dto.setAuthor("글쓴이1");
        dto.setContent("내용1");
        dto.setPassword("pwd1111");

        ModelMapper mapper = new ModelMapper();
        PostEntity entity = mapper.map(dto, PostEntity.class);

        postRepository.save(entity);

        Assertions.assertThat(postRepository.findAll().size()).isEqualTo(1);
        Assertions.assertThat(postRepository.findAll().get(0).getTitle()).isEqualTo(dto.getTitle());

    }
}
