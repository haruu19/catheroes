package com.web.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.model.Article;
import com.web.blog.model.Comment;
import com.web.blog.model.request.ArticleRequest;
import com.web.blog.model.request.CommentRequest;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.service.ArticleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RequestMapping("/api/v1")
@RestController
public class ArticleController {

	@Autowired
	ArticleService articleService;
	
	@GetMapping("/article/")
	@ApiOperation(value="포스트 전체 조회")
	public ResponseEntity<List<Article>> getAllArticle(){
		return ResponseEntity.ok().body(articleService.findAll());
	}
	
	@GetMapping("/article/member/{mid}")
	@ApiOperation(value="사용자 게시글 조회")
	public ResponseEntity<List<Article>> getMemberArticle(@PathVariable long mid){
		return ResponseEntity.ok().body(articleService.findArticleByMemberid(mid));
	}
	
	@GetMapping("/article/detail/{articleid}")
	@ApiOperation(value="포스트 상세 조회")
	public ResponseEntity<Article> getArticle(@PathVariable long articleid){
		Article test = articleService.findByArticleId(articleid);
		System.out.println(test.getMember());
		return ResponseEntity.ok().body(articleService.findByArticleId(articleid));
	}

	//수정해야함(DTO만들어서 해결해야할듯)
	@PostMapping("/article/")
	@ApiOperation(value="포스트 등록")
	public ResponseEntity<Article> saveArticle(@RequestParam Map<String,String> request,@RequestPart("file") MultipartFile file){
		ObjectMapper objectMapper = new ObjectMapper();		
		ArticleRequest articleRequest = objectMapper.convertValue(request,ArticleRequest.class);
		articleRequest.setFile(file);
		// System.out.println(articleRequest.getCatid());

		// System.out.println(articleRequest.toString());
		return ResponseEntity.ok().body(articleService.saveArticle(articleRequest));
	}
	@PutMapping("/article/{articleid}")
	@ApiOperation(value="포스트 수정")
	public ResponseEntity<Article> updateArticle(@PathVariable long articleid, @RequestBody Article article){
		article.setArticleid(articleid);
		return ResponseEntity.ok().body(articleService.updateArticle(article));
	}
	
	@DeleteMapping("/article/{articleid}")
	@ApiOperation(value = "포스트 삭제")
	public HttpStatus deleteArticle(@PathVariable long articleid) {
		articleService.deleteByArticleId(articleid);
		return HttpStatus.OK;
	}
	
	@GetMapping("/article/comment/{articleid}")
	@ApiOperation(value = "commnet 조회")
	public ResponseEntity<List<Comment>> getCommentByArticleId(@PathVariable long articleid) {
		return ResponseEntity.ok().body(articleService.findCommentByArticleArticleid(articleid));
	}
	
	@PostMapping("/article/comment")
	@ApiOperation(value = "댓글 등록하기")
	public ResponseEntity<Comment> saveComment(@RequestBody CommentRequest commentRequest) {
		System.out.println(commentRequest.toString());
		return ResponseEntity.ok().body(articleService.saveComment(commentRequest));
	}
	
	@DeleteMapping("/comment/{commentid}")
	@ApiOperation(value = "댓글 삭제하기")
	public HttpStatus deleteComment(@PathVariable long commentid) {
		System.out.println(commentid);
		articleService.deleteComment(commentid);
		return HttpStatus.OK;
	}

	@GetMapping("/article/cat/{catid}")
	@ApiOperation(value = "고양이 번호로 게시글 조회")
	public ResponseEntity<List<Article>> getCatArticle(@PathVariable long catid){
		return ResponseEntity.ok().body(articleService.findArticleByCatid(catid));
	}

	@GetMapping("/article/newArticle")
	@ApiOperation(value = "새로운 게시글 알림")
	public ResponseEntity<List<Article>> getNewArticle(@RequestParam long mid) {
		return ResponseEntity.ok().body(articleService.findNewArticle(mid));
	}
}