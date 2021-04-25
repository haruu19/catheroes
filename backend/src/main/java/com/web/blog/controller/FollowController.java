package com.web.blog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.web.blog.dao.FollowDao;
import com.web.blog.model.Follow;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.model.response.FollowResponse;
import com.web.blog.service.FollowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RequestMapping("/api/v1")
@RestController
public class FollowController {

	@Autowired
	private FollowService followService;

	@GetMapping("/follow/cat/{catid}")
	@ApiOperation(value = "팔로워 찾기")
	public ResponseEntity<List<Follow>> getFollowByCatid(@PathVariable long catid) {
		return ResponseEntity.ok().body(followService.findByCatCatid(catid));
	}

	@GetMapping("/follow/member/{mid}")
	@ApiOperation(value = "팔로우한 고양이 찾기")
	public ResponseEntity<List<Follow>> getFollowByMid(@PathVariable long mid) {
		return ResponseEntity.ok().body(followService.findByMebmerMid(mid));
	}

	@PostMapping("/follow")
	@ApiOperation(value = "팔로우 추가/제거")
	public ResponseEntity<Boolean> followCat(@RequestParam(required = true) final long catid,
											 @RequestParam(required = true) final long mid){
		Optional<Follow> followDb = followService.isFollow(catid, mid);		

		//팔로우가 존재
		if (followDb.isPresent()) {
			// System.out.println("존재");
			followService.deleteByCatCatidAndMemberMid(catid, mid);
			return ResponseEntity.ok().body(false);
		} else {//팔로우가 존재하지않음
			// System.out.println("안존재");
			followService.saveFollow(catid, mid);
			return ResponseEntity.ok().body(true);
		}		
	}

	@PostMapping("/follow/check")
	@ApiOperation(value = "팔로우 체크")
	public ResponseEntity<Boolean> followCheck(long catid, long mid) {
		Optional<Follow> followDb = followService.isFollow(catid, mid);		

		if (followDb.isPresent()) {
			return ResponseEntity.ok().body(true);
		} else {
			return ResponseEntity.ok().body(false);
		}
	}
}