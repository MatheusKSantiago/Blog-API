package com.learn.blog.article.service;

import com.learn.blog.article.dtos.CommentDTO;
import com.learn.blog.article.entity.Article;
import com.learn.blog.article.entity.Comment;
import com.learn.blog.article.exceptions.CommentException;
import com.learn.blog.article.repository.CommentRepository;
import com.learn.blog.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public void persist(CommentDTO comment, Article article){
        var commentEntity = new Comment();
        commentEntity.setUser(userService.getUser());
        commentEntity.setText(comment.text());
        commentEntity.setArticle(article);
        commentRepository.save(commentEntity);
    }

    public void delete(long id){
        getPermission(id);
        commentRepository.deleteById(id);
    }


    private void getPermission(long id){
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(()->new CommentException("Comment doesnt exists"));

        if(!userService.getUser().getId().equals(comment.getUser().getId()))
            throw new CommentException("ACESS DENIED");
    }
}
