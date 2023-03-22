package com.example.projectboard.service;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.type.SearchType;
import com.example.projectboard.dto.ArticleDto;
import com.example.projectboard.dto.ArticleUpdateDto;
import com.example.projectboard.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks private ArticleService sut;            //@InjectMocks: Mock을 주입하는 대상

    @Mock private ArticleRepository articleRepository;  //@Mock: 그 외 Mock들


    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        //given

        //when
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search Keyword");   //제목, 본문, ID, 닉네임, 해시태그

        //then
        assertThat(articles).isNotNull();
    }


    @DisplayName("게시글을 조회하면, 게시글을 반환한다.")
    @Test
    void givenId_whenSearchingArticle_thenReturnsArticle() {
        //given

        //when
        ArticleDto articles = sut.searchArticle(1L);

        //then
        assertThat(articles).isNotNull();
    }





    @DisplayName("게시글 정보를 입력하면, 게시글을 생성한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle() {
        //given
        ArticleDto dto = ArticleDto.of(LocalDateTime.now(), "Joe", "title", "content", "#java");
        //BDDMockito를 static import - BDDMockito.given(), BDDMockito.then()
        given(articleRepository.save(ArgumentMatchers.any(Article.class))).willReturn(null);  //실제 DB에 반영되는 코드가 아니다.

        //when
        sut.saveArticle(dto);

        //then
        then(articleRepository).should().save(ArgumentMatchers.any(Article.class));  //articleRepository.save()가 호출되었는지 검사
    }

    @DisplayName("게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")
    @Test
    void givenArticleIDAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle() {
        //given
        ArticleUpdateDto dto = ArticleUpdateDto.of("title", "content", "#java");
        //BDDMockito를 static import - BDDMockito.given(), BDDMockito.then()
        given(articleRepository.save(ArgumentMatchers.any(Article.class))).willReturn(null);  //실제 DB에 반영되는 코드가 아니다.

        //when
        sut.updateArticle(1L, dto);

        //then
        then(articleRepository).should().save(ArgumentMatchers.any(Article.class));  //articleRepository.save()가 호출되었는지 검사
    }

    @DisplayName("게시글의 ID를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleID_whenDeletingArticle_thenDeletesArticle() {
        //given
        ArticleUpdateDto dto = ArticleUpdateDto.of("title", "content", "#java");
        //BDDMockito를 static import - BDDMockito.willDoNothing(), BDDMockito.given(), BDDMockito.then()
        willDoNothing().given(articleRepository).delete(ArgumentMatchers.any(Article.class));

        //when
        sut.deleteArticle(1L);

        //then
        then(articleRepository).should().delete(ArgumentMatchers.any(Article.class));  //articleRepository.save()가 호출되었는지 검사
    }
}