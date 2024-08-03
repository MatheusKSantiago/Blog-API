package com.learn.blog.article;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Transactional
    public ResponseEntity<String> article(@ModelAttribute @Valid ArticleCreationDTO articleCreationDTO)
            throws IOException
    {
        if (articleCreationDTO.images().length != articleCreationDTO.imageDescriptions().length)
            return ResponseEntity.badRequest().body("number of images doesnt match the number of descriptions");

        articleService.save(articleCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> article(@PathVariable long id){
        articleService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
