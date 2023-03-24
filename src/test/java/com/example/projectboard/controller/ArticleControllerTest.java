package com.example.projectboard.controller;

import com.example.projectboard.config.SecurityConfig;
import com.example.projectboard.dto.ArticleWithCommentsDto;
import com.example.projectboard.dto.UserAccountDto;
import com.example.projectboard.service.ArticleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 게시글")
@Import(SecurityConfig.class)           //시큐리티 설정 import
@WebMvcTest(ArticleController.class)    //테스트할 컨트롤러만 속성으로 입력시 해당 컨트롤러만 읽어 들인다.
class ArticleControllerTest {

    private final MockMvc mvc;

    @MockBean private ArticleService articleService;

    ArticleControllerTest(@Autowired MockMvc mvc) {     //원래 생성자가 하나면 @Autowired 가 자동을 붙어지지만 test 패키지에서는 아니다.
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        //given
        given(articleService.searchArticles(eq(null), eq(null), any(Pageable.class))).willReturn(Page.empty());      //필드 중 일부만 ArgumentMatcher를 사용 할 수 없다. null 값들도 ArgumentMatcher.eq()로 감싸주자.

        //when & then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))      //html 형식(하위 형식들 포함) 확인(html/charset-utf8)
                .andExpect(view().name("articles/index"))   //뷰 이름 검사
                .andExpect(model().attributeExists("articles"));    //model.addAttribute()로 model에 articles 데이터가 담겨있는지 확인

        then(articleService).should().searchArticles(eq(null), eq(null), any(Pageable.class));
    }


    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
        //given
        Long articleId = 1L;
        given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());

        //when & then
        mvc.perform(get("/articles/" + articleId))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))      //html 형식 확인
                .andExpect(view().name("articles/detail"))   //뷰 이름 검사
                .andExpect(model().attributeExists("article"))    //model.addAttribute()로 model에 article 데이터가 담겨있는지 확인
                .andExpect(model().attributeExists("articleComments"));    //model.addAttribute()로 model에 articleComments 데이터가 담겨있는지 확인

        then(articleService).should().getArticle(articleId);
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))      //html 형식 확인
                .andExpect(view().name("articles/search")); //뷰 이름 검사
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))      //html 형식 확인
                .andExpect(view().name("articles/search-hashtag"));   //뷰 이름 검사
    }

    private ArticleWithCommentsDto createArticleWithCommentsDto() {
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(1L,
                "uno",
                "pw",
                "uno@mail.com",
                "Uno",
                "memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }
}