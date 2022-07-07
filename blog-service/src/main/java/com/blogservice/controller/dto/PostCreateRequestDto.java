package com.blogservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sun.istack.NotNull;
import java.io.Serializable;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PostCreateRequestDto implements Serializable {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String content;

    @NotNull
    @Size(min = 6, message = "password not be less than 6 characters")
    private String password;
}
