package com.web.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.web.blog.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private static final String[] EXCLUDE_PATHS = {
//			"/account/**",
//			"/error/**",
//			"/v2/api-docs", 
//			"/swagger-resources/**", 
//			"/swagger-ui.html", 
//			"/webjars/**", 
//			"/swagger/**",
//			"/auth/**",
			"/**"
			};

	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
						//.addPathPatterns("/**")
						.excludePathPatterns(EXCLUDE_PATHS);
	}

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        		.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD");
    }
}
