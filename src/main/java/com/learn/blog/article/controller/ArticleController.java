package com.learn.blog.article.controller;

import com.learn.blog.article.entity.Article;
import com.learn.blog.article.enums.OrderBy;
import com.learn.blog.article.service.ArticleService;
import com.learn.blog.article.dtos.ArticleCreationDTO;
import com.learn.blog.article.dtos.ArticleUpdateDTO;
import com.learn.blog.article.exceptions.ArticleException;
import com.learn.blog.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("article")
public class ArticleController {

    private ArticleService articleService;
    private UserService userService;

    public ArticleController(ArticleService articleService,
                             UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<Void> article(@ModelAttribute @Valid ArticleCreationDTO articleCreationDTO)
            throws IOException
    {

        articleService.save(articleCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> articleDelete(@PathVariable long id){

        articleService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping(path = "{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> articleUpdate(@PathVariable long id,
                                                @ModelAttribute @Valid ArticleUpdateDTO article){

        articleService.update(id,article);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("{id}/like")
    public ResponseEntity<Long> articleLike(@PathVariable long id){
        Long numberOfLikes =  articleService.like(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(numberOfLikes);
    }

    @GetMapping("page/{page}")
    public ResponseEntity<List<Article>> getArticle(@PathVariable int page,
                                                    @RequestParam(defaultValue = "2") int size,
                                                    @RequestParam(defaultValue = "NUMBER_OF_LIKES") OrderBy order){

        return ResponseEntity.ok(articleService.getPage(page,size,order));
    }
}
