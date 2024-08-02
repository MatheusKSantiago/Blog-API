package com.learn.blog.article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleImageRepository extends JpaRepository<ArticleImage,Long> {
}
