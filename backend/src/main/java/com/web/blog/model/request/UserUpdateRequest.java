package com.web.blog.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Valid
@ToString
public class UserUpdateRequest extends UserSignupRequest{
    @ApiModelProperty(required = true)
    @NotNull
    long uid;
    
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}
}
