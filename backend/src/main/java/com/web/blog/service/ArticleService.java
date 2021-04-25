package com.web.blog.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.Article;
import com.web.blog.model.Comment;
import com.web.blog.model.request.ArticleRequest;
import com.web.blog.model.request.CommentRequest;

public interface ArticleService {
	//포스트 전체 조회
	List<Article> findAll();
	//포스트 상세조회
	Article findByArticleId(long articleid);
	//포스트 등록
	Article saveArticle(ArticleRequest articleRequest);
	//포스트 삭제
	void deleteByArticleId(long articleid);
	//포스트 수정
	Article updateArticle(Article article);
	//유저가 작성한 글 보기
	List<Article> findArticleByMemberid(long mid);
	//고양이 번호로 게시글 조회
	List<Article> findArticleByCatid(long catid);
	//코멘트 조회
	List<Comment> findCommentByArticleArticleid(long articleid);
	//코멘트 등록
	Comment saveComment(CommentRequest commentRequest);
	//코멘트 삭제
	void deleteComment(long commentid);
	//새로운 게시글 알림
	List<Article> findNewArticle(long mid);
}
