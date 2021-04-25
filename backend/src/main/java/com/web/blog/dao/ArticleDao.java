
package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.web.blog.model.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleDao extends JpaRepository<Article, String> {
    Optional<Article> findArticleByArticleid(long articleid);
    
    List<Article> findByMemberMidOrderByArticleidDesc(long mid);
    List<Article> findByCatCatidOrderByArticleidDesc(long catid);
    @Transactional
    @Query(value=" SELECT max(articleid) "
    		   + " FROM article ", nativeQuery = true)
    Long getMaxArticleId() ;    
    
    @Transactional
    @Query(value=" SELECT * "
    		   + " FROM article "
               + " WHERE catid IN (SELECT catid FROM follow WHERE mid = :mid) AND mid != :mid "
               + " ORDER BY articleid DESC "
               + " LIMIT 10", nativeQuery = true)
    List<Article> newArticle(@Param("mid")Long mid);

}
