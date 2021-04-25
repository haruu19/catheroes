package com.web.blog.model.request;



import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ArticleRequest{
    private long articleid;
    private long mid;
    private long catid;
    private String title;
    private String content;
    private String image;    
    private MultipartFile file;
}