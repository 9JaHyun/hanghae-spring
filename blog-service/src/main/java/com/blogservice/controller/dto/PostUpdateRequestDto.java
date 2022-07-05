package com.blogservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class PostUpdateRequestDto implements Serializable {

    private Long id;
    private String title;
    private String content;
    private String password;

}
