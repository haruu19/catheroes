package com.web.blog.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@ToString
public class CommentSaveRequest {
    @ApiModelProperty(required = true)
    @NotNull
    long articleid;
    @ApiModelProperty(required = true)
    @NotNull
    String comment;
    @ApiModelProperty(required = true)
    @NotNull
    String writer;
    
	public long getArticleid() {
		return articleid;
	}
	public void setArticleid(long articleid) {
		this.articleid = articleid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}   
}