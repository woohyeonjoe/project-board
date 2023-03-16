package com.example.projectboard.controller;

import com.example.projectboard.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@DisplayName("View 컨트롤러 - 인증")
@Import(SecurityConfig.class)           //시큐리티 설정 import
@WebMvcTest
public class AuthControllerTest {

    private final MockMvc mvc;

    AuthControllerTest(@Autowired MockMvc mvc) {     //원래 생성자가 하나면 @Autowired 가 자동을 붙어지지만 test 패키지에서는 아니다.
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 로그인 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenTryToLogin_thenReturnsLoginView() throws Exception {
        //given

        //when & then
        mvc.perform(get("/login"))
                .andExpect(status().isOk())                                 //200OK 확인
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));      //html 형식(하위 형식들 포함) 확인(html/charset-utf8)
    }
}
