package com.web.blog.dao;

import com.web.blog.model.Member;

public interface AuthService {
	void singUpUser(Member user);
	
	Member loginUser(String email, String password);
}
