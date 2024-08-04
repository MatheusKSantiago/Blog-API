package com.learn.blog.article.entity;


import jakarta.persistence.PrePersist;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ArticleListener {

    @PrePersist
    public void setDate(Article article){
        article.setDate(LocalDateTime.now());
    }
}
