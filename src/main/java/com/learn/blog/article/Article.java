package com.learn.blog.article;

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

    @OneToMany(mappedBy = "article")
    private List<ArticleImage> images;

    @ManyToOne
    private User user;

}
