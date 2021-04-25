package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.Follow;

public interface FollowDao extends JpaRepository<Follow, String> {
	//고양이 팔로우 한 사람 찾기
	List<Follow> findByCatCatid(long catid); 
	//팔로우한 고양이 찾기
	List<Follow> findByMemberMid(long mid);
    //팔로우 했는지 검색
    Optional<Follow> findByCatCatidAndMemberMid(long catid, long mid);
    //팔로우 삭제
    @Transactional
    void deleteByCatCatidAndMemberMid(long catid, long mid);

}
