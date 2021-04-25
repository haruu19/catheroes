package com.web.blog.model.response;

import javax.validation.Valid;

import lombok.Data;

@Valid
@Data
public class OAuthTokenResponse {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private int expires_in;
	private String scope;
	private int refresh_token_expires_in;
	
}
