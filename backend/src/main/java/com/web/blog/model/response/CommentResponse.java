package com.web.blog.model.response;

import io.swagger.annotations.ApiModelProperty;

public class CommentResponse extends BasicResponse {
    @ApiModelProperty(value = "commentid", position = 4)
    public long commentid;
    @ApiModelProperty(value = "articleid", position = 5)
    public long articleid;
    @ApiModelProperty(value = "comment", position = 6)
    public String comment;
    @ApiModelProperty(value = "writer", position = 7)
    public String writer;
    
}

