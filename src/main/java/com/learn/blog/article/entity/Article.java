package com.learn.blog.article.entity;

import com.learn.blog.article.entity.ArticleImage;
import com.learn.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Table(name="ARTICLE")
@Entity
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<ArticleImage> images;

    @ManyToOne
    private User user;

}