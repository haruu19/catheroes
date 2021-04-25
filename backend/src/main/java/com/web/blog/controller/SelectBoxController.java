package com.web.blog.controller;

import java.util.List;

import com.web.blog.model.SelectBox;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.service.SelectBoxService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class SelectBoxController {

	@Autowired
	private SelectBoxService selectBoxService;

	@PostMapping("/gugun")
	@ApiOperation(value = "구군 설정")
	public ResponseEntity<List<SelectBox>> getGugun(@RequestParam(value = "sidoname", required = true) final String sidoname) {
        return ResponseEntity.ok().body(selectBoxService.getGugun(sidoname));
	}
	
	@PostMapping("/dong")
	@ApiOperation(value = "동 설정")
	public ResponseEntity<List<SelectBox>> getDong(@RequestParam(value = "sidoname", required = true) final String sidoname, @RequestParam(value = "gugun", required = true) final String gugun) {
        return ResponseEntity.ok().body(selectBoxService.getDong(sidoname, gugun));
    }
}