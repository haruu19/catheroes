package com.web.blog.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Valid
@ToString
public class CatUpdateRequest{
	@ApiModelProperty(required = true)
    @NotNull
    long catid;
    @ApiModelProperty(required = true)
    @NotNull
    String breed;
    @ApiModelProperty(required = true)
    @NotNull
    String attr;
    @ApiModelProperty(required = true)
    @NotNull
    String conditions;
    
    
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public long getCatid() {
		return catid;
	}
	public void setCatid(long catid) {
		this.catid = catid;
	}
}
