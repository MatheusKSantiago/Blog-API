package com.learn.blog.article;

import com.learn.blog.article.dtos.ArticleCreationDTO;
import com.learn.blog.article.dtos.ArticleUpdateDTO;
import com.learn.blog.article.entity.Article;
import com.learn.blog.article.entity.ArticleImage;
import com.learn.blog.article.exceptions.ArticleException;
import com.learn.blog.article.repository.ArticleImageRepository;
import com.learn.blog.article.repository.ArticleRepository;
import com.learn.blog.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

    @Transactional
    public void update(long id, ArticleUpdateDTO article){
        Article articleEntity = articleRepository
                .findById(id)
                .orElseThrow(()->new ArticleException("Article doesnt exists"));

        Long[] ids = article.idImagesRemove();
        MultipartFile[] newImages = article.newImages();
        String[] descriptions = article.imagesDescriptions();

        if(newImages != null && descriptions != null)
            insertNewImages(newImages,descriptions,articleEntity);

        if(ids != null)
            removeImages(ids);

        articleEntity.setText(article.newText());

        articleRepository.save(articleEntity);
    }
    private void removeImages(Long[] ids){
        for(long id: ids){
            articleImageRepository.deleteById(id);
        }
    }

    private void insertNewImages(MultipartFile[] imagesFiles,String[] descriptions,Article article){
        if(imagesFiles.length != descriptions.length)
            throw new ArticleException("Number of images doesnt match number of descriptions");

        ArticleImage[] articleImages = getArticleImageFromMultipartFileArray(imagesFiles,article);

        for(int index=0;index<descriptions.length;index++){
            articleImages[index].setDescription(descriptions[index]);
        }
        articleImageRepository.saveAll(Arrays.asList(articleImages));
    }

    private ArticleImage[] getArticleImageFromMultipartFileArray(MultipartFile[] imagesFiles,Article article){
        return Arrays.stream(imagesFiles).map(imgFile ->{
            try{
                var a = new ArticleImage();
                a.setImg(imgFile.getBytes());
                a.setArticle(article);
                return a;
            }catch (IOException e){
                throw new RuntimeException("IOException during insertion of images");
            }
        }).toArray(ArticleImage[]::new);
    }

}
