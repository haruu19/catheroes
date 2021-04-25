package com.web.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.model.Article;
import com.web.blog.model.Donate;
import com.web.blog.model.Member;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.service.DonateService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RequestMapping("/api/v1")
@RestController
public class DonateController {

	@Autowired
	DonateService donateService;
	
	@GetMapping("/donate")
	@ApiOperation(value="후원 리스트 전체 조회")
	public ResponseEntity<List<Donate>> getAllDonate() {
		return ResponseEntity.ok().body(donateService.findAll());
	}
	
	@PutMapping("/donate")
	@ApiOperation(value="did에 해당하는 후원 모금액 수정")
	public ResponseEntity<List<Donate>> updateDonate(@RequestParam long did, @RequestParam long amount) {
		return ResponseEntity.ok().body(donateService.updateDonate(did, amount));
	}
}