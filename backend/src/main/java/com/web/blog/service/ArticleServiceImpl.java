package com.web.blog.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.opsworkscm.model.ResourceNotFoundException;
import com.web.blog.dao.ArticleDao;
import com.web.blog.dao.CommentDao;
import com.web.blog.dao.MemberDao;
import com.web.blog.model.Article;
import com.web.blog.model.Cat;
import com.web.blog.model.Comment;
import com.web.blog.model.Member;
import com.web.blog.model.request.ArticleRequest;
import com.web.blog.model.request.CommentRequest;
import com.web.blog.utill.amazon.AmazonClient;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	ArticleDao articleDao;
	
	@Autowired
	MemberDao memberDao;	
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Override
	public List<Article> findAll() {
		return articleDao.findAll();
	}

	@Override
	public Article findByArticleId(long articleid) {
		Optional<Article> articleDb = articleDao.findArticleByArticleid(articleid); 
		if(articleDb.isPresent()) {
			return articleDb.get();
		}else {
			throw new ResourceNotFoundException("Record not found with articleid : " + articleid);			
		}
	}

	@Override
	public Article saveArticle(ArticleRequest articleRequest) {
		Article article = new Article();
		System.out.println(articleRequest.toString());
		try {
			String path = this.amazonClient.uploadFile(articleRequest.getFile(), articleDao.getMaxArticleId()+1,"post/");
			
			Member member = new Member();			
			Cat cat = new Cat();
			member.setMid(articleRequest.getMid());
			cat.setCatid(articleRequest.getCatid());
			article.setCat(cat);
			article.setMember(member);
			article.setImage(path);
			article.setContent(articleRequest.getContent());
			article.setTitle(articleRequest.getTitle());		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articleDao.save(article);

	}

	@Override
	public void deleteByArticleId(long articleid) {
		Optional<Article> articleDb = articleDao.findArticleByArticleid(articleid);
		if(articleDb.isPresent()) {
			articleDao.delete(articleDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with articleid : " + articleid);
		}
	}

	@Override
	public Article updateArticle(Article article) {
		Optional<Article> articleDb = articleDao.findArticleByArticleid(article.getArticleid());
		if(articleDb.isPresent()) {
			Article articleUpdate = articleDb.get();
			articleUpdate.setContent(article.getContent());
			articleUpdate.setTitle(article.getTitle());
			articleUpdate.setImage(article.getImage());
			return articleDao.save(articleUpdate);
		}else {
			throw new ResourceNotFoundException("Record not found with articleid : "+article.getArticleid());
		}
		
	}

	@Override
	public List<Article> findArticleByMemberid(long mid) {
		return articleDao.findByMemberMidOrderByArticleidDesc(mid);
	}

	@Override
	public List<Comment> findCommentByArticleArticleid(long articleid) {		
		return commentDao.findCommentByArticleArticleid(articleid);
	}

	@Override
	public Comment saveComment(CommentRequest commentRequest) {
		System.out.println(commentRequest.toString());
		Comment comment = new Comment();
		comment.setComment(commentRequest.getComment());
		comment.setWriter(commentRequest.getWriter());
		Article article = new Article();
		article.setArticleid(commentRequest.getArticleid());
		comment.setArticle(article);
		return commentDao.save(comment);
	}

	@Override
	public void deleteComment(long commentid) {
		Optional<Comment> commentDb = commentDao.findByCommentid(commentid);
		
		if(commentDb.isPresent()) {
			commentDao.delete(commentDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with commentid : " + commentid);			
		}
	}

	@Override
	public List<Article> findArticleByCatid(long catid) {
		return articleDao.findByCatCatidOrderByArticleidDesc(catid);
	}

	@Override
	public List<Article> findNewArticle(long mid) {
		return articleDao.newArticle(mid);
	}
	
}
