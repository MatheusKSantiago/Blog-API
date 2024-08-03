package com.learn.blog.article.dtos;

import jakarta.validation.constraints.NotBlank;

public record CommentDTO(@NotBlank String text) {
}
