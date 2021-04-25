package com.web.blog.model.response;

import io.swagger.annotations.ApiModelProperty;

public class ArticleResponse extends BasicResponse {
    @ApiModelProperty(value = "article", position = 4)
    public long articleid;
    @ApiModelProperty(value = "catid", position = 5)
    public long catid;
    @ApiModelProperty(value = "userid", position = 6)
    public long userid;
    @ApiModelProperty(value = "title", position = 7)
    public String title;
    @ApiModelProperty(value = "content", position = 8)
    public String content;
    @ApiModelProperty(value = "image", position = 9)
    public String image;
}

