package com.blogservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PostResponseDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
