package com.blogservice.service;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.blogservice.controller.PostController;
import com.blogservice.controller.dto.PostResponseDto;
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
              .apply(documentationConfiguration(restDocumentation)).build();
    }

    @DisplayName(value = "전체 게시글 출력 테스트")
    @Test
    void findAllTest() throws Exception {
        List<PostResponseDto> dtos = new ArrayList<>();

        given(postService.findAll("id")).willReturn(dtos);

        this.mockMvc.perform(get("/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"))
              .andDo(MockMvcResultHandlers.print())
              .andExpectAll(status().isOk(), status().isNoContent())
              .andDo(document("posts-get",
                    responseFields(
                          fieldWithPath("[]").description("게시글 리스트"),
                          fieldWithPath("[].id").description("게시글 ID"),
                          fieldWithPath("[].title").description("게시글 제목"),
                          fieldWithPath("[].author").description("글쓴이"),
                          fieldWithPath("[].content").description("내용")
                    )));
    }
}
