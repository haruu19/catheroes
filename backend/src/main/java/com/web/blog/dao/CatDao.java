
package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.model.Cat;

public interface CatDao extends JpaRepository<Cat, String> {
	//고양이 상세조회
    Optional<Cat> findCatByCatid(long catid);
    //고양이 전체검색->위치
    List<Cat> findCatByLocation(String location);
    //고양이 검색->종(이미지검색)
    List<Cat> findCatByLocationAndBreed(String location, String breed);
    
    @Transactional
    @Query(value=" SELECT max(catid) "
    + " FROM cat ", nativeQuery = true)
    Long getMaxCatId() ;


    
    //고양이 이름으로 검색
    @Transactional
    @Query(value=" SELECT * "
               + " FROM cat "
               + " where location = :location and (nickname like %:nickname% or breed like %:nickname%)", nativeQuery = true)
    List<Cat> findCatByLocationAndNicknameContaining(@Param("location") String location, @Param("nickname") String nickname);
}
