package com.learn.blog.article.entity;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "who_likes",
    joinColumns = @JoinColumn(name="article_id"),
    inverseJoinColumns = @JoinColumn(name="user_id"))
    public List<User> whoLikes;

    public long numberOfLikes;

    @ManyToOne
    private User author;

}
