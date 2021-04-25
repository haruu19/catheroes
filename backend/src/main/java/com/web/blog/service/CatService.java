package com.web.blog.service;

import java.util.List;

import com.web.blog.model.Cat;
import com.web.blog.model.Follow;
import com.web.blog.model.request.CatRequest;

public interface CatService {
	//고양이 전체 조회->위치
	List<Cat> findCatByLocation(String location);
	//고양이 상세조회
	Cat findCatByCatId(long catid);
	//고양이 검색->이름
	List<Cat> findCatByNickName(String nickname, String location);
	//고양이 검색->종(사진)
	List<Cat> findCatByBreed(String breed, String location);
	//고양이 등록
	Cat saveCat(CatRequest catRequest);
	//고양이 삭제
	void deleteByCatId(long catid);
	//고양이 수정
	Cat updateCat(CatRequest catRequest);
	//고양이 팔로우
	List<Follow> findFollowByCatid(long catid);
	
}
