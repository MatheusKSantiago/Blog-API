package com.learn.blog.article;

import com.learn.blog.user.UserRepository;
import com.learn.blog.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;
    private ArticleImageRepository articleImageRepository;

    private UserService userService;

    public ArticleService(ArticleRepository articleRepository,
                          ArticleImageRepository articleImageRepository,
                          UserService userService) {
        this.articleRepository = articleRepository;
        this.articleImageRepository = articleImageRepository;
        this.userService = userService;
    }


    @Transactional
    public void save(ArticleImageDTO imageDTO,String articleText) throws IOException {
        var article = new Article();
        article.setText(articleText);
        article.setUser(userService.getUser());
        articleRepository.save(article);

        for(int index=0; index<imageDTO.images().length; index++){
            var articleImage = new ArticleImage();
            articleImage.setArticle(article);
            articleImage.setImg(imageDTO.images()[index].getBytes());
            articleImage.setDescription(imageDTO.descriptions()[index]);
            articleImageRepository.save(articleImage);
        }
    }
}
