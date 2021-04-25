package com.web.blog.model.response;

import io.swagger.annotations.ApiModelProperty;

public class FollowResponse extends BasicResponse{
    @ApiModelProperty(value = "fcid", position = 4)
    public long fcid;
    @ApiModelProperty(value = "catid", position = 5)
    public long catid;
    @ApiModelProperty(value = "userid", position = 6)
    public long userid;
}

