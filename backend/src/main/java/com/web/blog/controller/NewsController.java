package com.web.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.News;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.model.response.NewsResponse;
import com.web.blog.service.NewsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RequestMapping("/api/v1")
@RestController
public class NewsController {
	
	@Autowired
	private NewsService newsService;
	
	/* 예상 시나리오 : 
	 * 프론트에서 뉴스 탭 클릭 -> 뉴스컨트롤러로 요청 보냄 -> 뉴스컨트롤러에서 뉴스 서비스 호출 후 뉴스정보 리스트 리턴 -> 프론트에서 응답데이터 받은 후 화면에 출력
	 * 
	 * */
    @GetMapping("/news")
    @ApiOperation(value = "뉴스")
    public Object newsList() {
    	
    	ResponseEntity response = null;
        
        // newsService를 통해 뉴스 정보를 받아옴
        Optional<List<News>> newsListOpt = newsService.retrieveNews();
        if(newsListOpt.isPresent()) {
        	
        	final List<NewsResponse> results = new ArrayList<NewsResponse>();
        	
        	for(News news : newsListOpt.get()) {
        		NewsResponse result = new NewsResponse();
        		result.status = true;
        		result.data = "success";
        		result.title = news.getTitle();
        		result.url = news.getUrl();
        		result.img = news.getImg();
        		result.content = news.getContent();
        		results.add(result);
        	}
        	
        	response = new ResponseEntity<>(results, HttpStatus.OK);

        } else {
        	response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }
}