package com.learn.blog.article.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name="ARTICLE_IMAGE")
@Entity
@Data
public class ArticleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BLOB")
    private byte[] img;

    @ManyToOne
    private Article article;

    private String description;
}
