package com.web.blog.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.web.blog.model.SelectBox;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SelectBoxDao extends JpaRepository<SelectBox, String> {
    
    @Transactional
    @Query(value=" SELECT * "
    		   + " FROM select_box  "
               + " WHERE sido = :sidoname "
               + " GROUP BY GUGUN", nativeQuery = true)
    List<SelectBox> getGugun(@Param("sidoname") String sidoname); 

    @Transactional
    @Query(value=" SELECT * "
    		   + " FROM select_box  "
               + " WHERE sido = :sidoname and gugun = :gugun "
               + " GROUP BY dong", nativeQuery = true)
    List<SelectBox> getDong(@Param("sidoname") String sidoname, @Param("gugun") String gugun); 
}
