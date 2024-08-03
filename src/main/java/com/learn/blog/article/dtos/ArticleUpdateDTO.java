package com.learn.blog.article.dtos;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record ArticleUpdateDTO(@NotBlank String newText,
                               MultipartFile[] newImages,
                               String[] imagesDescriptions,
                               Long[] idImagesRemove) {
}
