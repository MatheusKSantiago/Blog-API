package com.learn.blog.article.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public record ArticleCreationDTO(@NotEmpty MultipartFile[] images,
                                 @NotEmpty String[] imageDescriptions,
                                 @NotBlank String text) {
}
