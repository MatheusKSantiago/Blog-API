package com.learn.blog.article;

import com.learn.blog.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    public void save(ArticleCreationDTO articleCreationDTO) throws IOException {
        var article = new Article();
        article.setText(articleCreationDTO.text());
        article.setUser(userService.getUser());
        articleRepository.save(article);

        for(int index=0; index<articleCreationDTO.images().length; index++){
            var articleImage = new ArticleImage();
            articleImage.setArticle(article);
            articleImage.setImg(articleCreationDTO.images()[index].getBytes());
            articleImage.setDescription(articleCreationDTO.imageDescriptions()[index]);
            articleImageRepository.save(articleImage);
        }
    }

    public void delete(long id){
        articleRepository.deleteById(id);
    }
}
