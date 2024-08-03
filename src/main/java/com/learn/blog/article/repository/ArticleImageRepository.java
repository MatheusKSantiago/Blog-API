package com.learn.blog.article.repository;

import com.learn.blog.article.entity.ArticleImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImage,Long> {
}
