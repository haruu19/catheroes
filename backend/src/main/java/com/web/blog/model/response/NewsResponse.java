package com.web.blog.model.response;

import io.swagger.annotations.ApiModelProperty;

public class NewsResponse extends BasicResponse{
    @ApiModelProperty(value = "title", position = 4)
    public String title;
    @ApiModelProperty(value = "url", position = 5)
    public String url;
    @ApiModelProperty(value = "img", position = 6)
    public String img;
    @ApiModelProperty(value = "content", position = 7)
    public String content;
}

