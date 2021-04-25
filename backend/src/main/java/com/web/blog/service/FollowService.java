package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import com.web.blog.model.Follow;

public interface FollowService {
	//팔로워 찾기
	List<Follow> findByCatCatid(long catid);
	//팔로우한 고양이 찾기
	List<Follow> findByMebmerMid(long mid);
	//팔로우 삭제
	void deleteByCatCatidAndMemberMid(long catid, long mid);
	//팔로우 추가
	Follow saveFollow(long catid, long mid);
	//팔로우 체크
	Optional<Follow> isFollow(long catid, long mid);
}
