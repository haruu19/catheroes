package com.web.blog.model.request;

import org.springframework.web.multipart.MultipartFile;

import com.web.blog.model.Cat;

import lombok.Data;

@Data
public class CatRequest {
	// 고양이 정보
	// private Cat cat;
	private long catid;
	private String breed;
	private String attr;
	private String conditions;
	private String neutered;

	private String lat;
	private String lng;
	private String location;
	private String nickname;

	private String food;
	// 고양이 사진
	// private MultipartFile file;
	private MultipartFile file;

}
