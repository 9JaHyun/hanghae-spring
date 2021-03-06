package com.blogservice.service;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.blogservice.controller.PostController;
import com.blogservice.controller.dto.PostCreateRequestDto;
import com.blogservice.controller.dto.PostDeleteRequestDto;
import com.blogservice.controller.dto.PostResponseDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class PostDocumentationTest {

    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext,
          RestDocumentationContextProvider restDocumentation) throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
              .apply(documentationConfiguration(restDocumentation))
              .build();
    }

    @DisplayName(value = "????????? ?????? ?????? ?????????")
    @Test
    void findByIdTest() throws Exception {
        PostResponseDto responseDto = new PostResponseDto(1L, "title1", "author1", "content1", LocalDateTime.now(), LocalDateTime.now());

        given(postService.findById(1L)).willReturn(responseDto);

        this.mockMvc.perform(get("/post")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .param("id", "1"))
              .andDo(MockMvcResultHandlers.print())
              .andExpect(status().isOk())
              .andDo(document("singlePost-get",
                    responseFields(
                          fieldWithPath("id").description("????????? ID"),
                          fieldWithPath("title").description("????????? ??????"),
                          fieldWithPath("author").description("?????????"),
                          fieldWithPath("content").description("??????"),
                          fieldWithPath("createdAt").description("??????"),
                          fieldWithPath("updatedAt").description("??????")
                    )));
    }

    @DisplayName(value = "?????? ????????? ?????? ?????????")
    @Test
    void findAllTest() throws Exception {
        List<PostResponseDto> dtos = new ArrayList<>();
        dtos.add(new PostResponseDto(1L, "??????1", "?????????1", "??????1", LocalDateTime.now(), LocalDateTime.now()));
        dtos.add(new PostResponseDto(2L, "??????2", "?????????2", "??????2", LocalDateTime.now(), LocalDateTime.now()));

        given(postService.findAll("id")).willReturn(dtos);

        this.mockMvc.perform(get("/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"))
              .andDo(MockMvcResultHandlers.print())
              .andExpect(status().isOk())
              .andDo(document("posts-get",
                    responseFields(
                          fieldWithPath("data").description("????????? ?????????"),
                          fieldWithPath("data[].id").description("????????? ID"),
                          fieldWithPath("data[].title").description("????????? ??????"),
                          fieldWithPath("data[].author").description("?????????"),
                          fieldWithPath("data[].content").description("??????"),
                          fieldWithPath("data[].createdAt").description("??????"),
                          fieldWithPath("data[].updatedAt").description("??????")
                    )));
    }

    @DisplayName(value = "????????? ?????????")
    @Test
    void postingTest() throws Exception {
        PostCreateRequestDto requestDto = new PostCreateRequestDto("??????1", "?????????1", "??????1",
              "pw12345");

        PostResponseDto responseDto = new PostResponseDto();

        given(postService.posting(requestDto)).willReturn(responseDto);

        this.mockMvc.perform(post("/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content("{\n"
                          + "  \"title\": \"title3\",\n"
                          + "  \"author\": \"author3\",\n"
                          + "  \"password\": \"pwd13\",\n"
                          + "  \"content\": \"content3\"\n"
                          + "}"))
              .andDo(MockMvcResultHandlers.print())
              .andExpect(status().isCreated())
              .andDo(document("posts-post"));
    }

    @DisplayName(value = "????????? ?????? ?????????")
    @Test
    void updatePostTest() throws Exception {
        PostCreateRequestDto requestDto = new PostCreateRequestDto("??????1", "?????????1", "??????1",
              "pw12345");

        PostResponseDto responseDto = new PostResponseDto();

        given(postService.posting(requestDto)).willReturn(responseDto);

        this.mockMvc.perform(put("/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content("{\n"
                          + "  \"id\": 1,\n"
                          + "  \"title\": \"new_title1\",\n"
                          + "  \"password\": \"pwd13\",\n"
                          + "  \"content\": \"newContent_1\"\n"
                          + "}"))
              .andDo(MockMvcResultHandlers.print())
              .andExpect(status().isOk())
              .andDo(document("posts-update"));
    }

    @DisplayName(value = "????????? ?????? ?????????")
    @Test
    void deletePostTest() throws Exception {
        PostDeleteRequestDto requestDto = new PostDeleteRequestDto(1L, "pwd13");

        PostResponseDto responseDto = new PostResponseDto();

        this.mockMvc.perform(delete("/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content("{\n"
                          + "  \"id\": 1,\n"
                          + "  \"password\": \"pwd13\"\n"
                          + "}"))
              .andDo(MockMvcResultHandlers.print())
              .andExpect(status().isNoContent())
              .andDo(document("posts-delete"));
    }
}
