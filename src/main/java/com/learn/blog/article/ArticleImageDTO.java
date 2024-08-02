package com.learn.blog.article;

import org.springframework.web.multipart.MultipartFile;

public record ArticleImageDTO(MultipartFile[] images,String[] descriptions) {
}
