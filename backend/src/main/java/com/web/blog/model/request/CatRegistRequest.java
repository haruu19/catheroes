package com.web.blog.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@ToString
public class CatRegistRequest {
    @ApiModelProperty(required = true)
    @NotNull
    String nickname;
    @ApiModelProperty(required = true)
    @NotNull
    String breed;
    @ApiModelProperty(required = true)
    @NotNull
    String image;   
    @ApiModelProperty(required = true)
    @NotNull
    String lat;   
    @ApiModelProperty(required = true)
    @NotNull
    String lng;   

    
    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLng() {
        return lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
