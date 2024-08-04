package com.learn.blog.article.entity;

import com.learn.blog.article.repository.ArticleRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommentListener {



    @PostPersist
    public void incrementNumberOfComments(Comment comment){
        Article article = comment.getArticle();
        article.setNumberOfComments(article.getNumberOfComments()+1);
    }

    @PreRemove
    public void decrementNumberOfComments(Comment comment){
        Article article = comment.getArticle();
        article.setNumberOfComments(article.getNumberOfComments()-1);
    }
}
