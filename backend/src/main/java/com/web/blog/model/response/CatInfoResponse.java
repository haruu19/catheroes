package com.web.blog.model.response;

import io.swagger.annotations.ApiModelProperty;

public class CatInfoResponse extends BasicResponse{
    @ApiModelProperty(value = "nickName", position = 4)
    public String nickName;
    @ApiModelProperty(value = "age", position = 5)
    public int age;
    @ApiModelProperty(value = "breed", position = 6)
    public String breed;
    @ApiModelProperty(value = "location", position = 7)
    public String location;
    @ApiModelProperty(value = "attr", position = 8)
    public String attr;
    @ApiModelProperty(value = "food", position = 9)
    public String food;
    @ApiModelProperty(value = "hospital", position = 10)
    public String hospital;
    @ApiModelProperty(value = "family", position = 11)
    public String family;
    @ApiModelProperty(value = "neutered", position = 12)
    public String neutered;
    @ApiModelProperty(value = "conditions", position = 13)
    public String conditions;
    @ApiModelProperty(value = "image", position = 14)
    public String image;
    @ApiModelProperty(value = "lat", position = 15)
    public String lat;
    @ApiModelProperty(value = "lng", position = 16)
    public String lng;
    @ApiModelProperty(value = "catid", position = 17)
	public long catid;
}

