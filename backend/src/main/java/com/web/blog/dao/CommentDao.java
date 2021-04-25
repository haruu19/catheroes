
package com.web.blog.dao;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.model.Comment;

public interface CommentDao extends JpaRepository<Comment, String> {
	Optional<Comment> findByCommentid(long commentid);
    List<Comment> findCommentByArticleArticleid(long articleid);

}
