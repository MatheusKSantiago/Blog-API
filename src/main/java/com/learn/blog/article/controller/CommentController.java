package com.learn.blog.article.controller;

import com.learn.blog.article.dtos.CommentDTO;
import com.learn.blog.article.service.ArticleService;
import com.learn.blog.article.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {

    private ArticleService articleService;
    private CommentService commentService;

    public CommentController(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @PostMapping("article/{id}")
    public ResponseEntity<Void> postComment(@RequestBody @Valid CommentDTO comment,
                                            @PathVariable("id") long articleId){
        commentService.persist(comment,articleService.getArticleById(articleId));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id){
        commentService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
