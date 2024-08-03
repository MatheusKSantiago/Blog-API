package com.learn.blog.article.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvices extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ArticleException.class, CommentException.class})
    public ResponseEntity<String> handleArticleException(RuntimeException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
