package com.learn.blog.article;

import com.learn.blog.user.UserLoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("article")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<String> article(@RequestParam("images") MultipartFile[] images,
                                    @RequestPart("descriptions") String[] descriptions,
                                    @RequestParam("text") String text)
            throws IOException
    {
        if (images.length != descriptions.length)
            return ResponseEntity.badRequest().body("number of images doesnt match the number of descriptions");

        articleService.save(new ArticleImageDTO(images,descriptions),text);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
