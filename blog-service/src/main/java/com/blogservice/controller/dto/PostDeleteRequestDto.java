package com.blogservice.controller.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDeleteRequestDto {

    @NotNull
    private Long id;

    @NotNull
    private String password;
}
