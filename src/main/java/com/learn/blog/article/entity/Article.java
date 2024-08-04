package com.learn.blog.article.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.learn.blog.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name="ARTICLE")
@Entity
@Data
@EntityListeners(ArticleListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private List<ArticleImage> images;

    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "who_likes",
    joinColumns = @JoinColumn(name="article_id"),
    inverseJoinColumns = @JoinColumn(name="user_id"))
    public List<User> whoLikes;

    public long numberOfLikes;

    public long numberOfComments;

    public LocalDateTime date;

    @ManyToOne
    @JsonProperty(value = "authorEmail")
    private User author;

}
