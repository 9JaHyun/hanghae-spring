package com.blogservice.domain;

import lombok.Data;

// DTO로 변환
@Data
public class Post {

    private Long id;

    private String title;

    private String author;

    private String password;

    private String content;
}
