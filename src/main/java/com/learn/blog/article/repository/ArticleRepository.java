package com.learn.blog.article.repository;

import com.learn.blog.article.entity.Article;
import com.learn.blog.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Modifying
    @Query(value = "INSERT INTO WHO_LIKES (article_id,user_id) VALUES (:articleId,:userId)",nativeQuery = true)
    void saveWhoLike(@Param("articleId") long articleId, @Param("userId") long userId);
}
