package com.web.blog.model.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentRequest {
    @ApiModelProperty(required = true)
    @NotNull
    long articleid;
    @ApiModelProperty(required = true)
    @NotNull
    String comment;
    @ApiModelProperty(required = true)
    @NotNull
    String writer;
   
}