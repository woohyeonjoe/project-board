package com.example.projectboard.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)    //테스트할 컨트롤러만 속성으로 입력시 해당 컨트롤러만 읽어 들인다.
class ArticleControllerTest {

    private final MockMvc mvc;

    ArticleControllerTest(@Autowired MockMvc mvc) {     //원래 생성자가 하나면 @Autowired 가 자동을 붙어지지만 test 패키지에서는 아니다.
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentType(MediaType.TEXT_HTML))      //html 형식 확인
                .andExpect(model().attributeExists("articles"));    //model.addAttribute()로 model에 articles 데이터가 담겨있는지 확인
    }

    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentType(MediaType.TEXT_HTML))      //html 형식 확인
                .andExpect(model().attributeExists("article"));    //model.addAttribute()로 model에 article 데이터가 담겨있는지 확인
    }

    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentType(MediaType.TEXT_HTML));      //html 형식 확인
    }

    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentType(MediaType.TEXT_HTML));      //html 형식 확인
    }
}