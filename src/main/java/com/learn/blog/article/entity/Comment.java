package com.learn.blog.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.learn.blog.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="COMMENT_ARTICLE")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(CommentListener.class)
@JsonIgnoreProperties(value = {"user","id","article"})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    private Article article;

}
