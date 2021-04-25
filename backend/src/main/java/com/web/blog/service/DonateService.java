package com.web.blog.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.Article;
import com.web.blog.model.Comment;
import com.web.blog.model.Donate;
import com.web.blog.model.request.ArticleRequest;
import com.web.blog.model.request.CommentRequest;

public interface DonateService {
	//후원 리스트 전체 조회
	List<Donate> findAll();
	//후원 모금액 수정
	List<Donate> updateDonate(long did, long amount);
}
