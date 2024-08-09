package com.learn.blog.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.learn.blog.article.controller.ArticleController;
import com.learn.blog.article.exceptions.ArticleException;
import com.learn.blog.article.service.ArticleService;
import com.learn.blog.config.security.SecurityConfig;
import com.learn.blog.config.security.TokenService;
import com.learn.blog.user.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers = ArticleController.class)
@Import({TokenService.class})
@ImportAutoConfiguration(classes = {SecurityConfig.class})
public class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser("USER")
    public void controllerAdviceWorks() throws Exception {
        String message = "This message must be showed";

        doThrow(new ArticleException(message)).when(articleService).delete(1);
        
        mvc.perform(delete("/article/{id}",1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(message));
    }

}
