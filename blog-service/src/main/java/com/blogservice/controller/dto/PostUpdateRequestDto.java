package com.blogservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sun.istack.NotNull;
import java.io.Serializable;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class PostUpdateRequestDto implements Serializable {

    @NotNull
    private Long id;
    private String title;
    private String content;

    @NotNull
    private String password;
}
