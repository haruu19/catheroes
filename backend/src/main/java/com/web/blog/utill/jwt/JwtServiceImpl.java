package com.web.blog.utill.jwt;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.web.blog.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("jwtService")
public class JwtServiceImpl implements JwtService{
    private static final String SALT = "SSAFY";

    @Override
    public <T> String create(String key, T data, String subject){
        String jwt = Jwts.builder()
                                        .setHeaderParam("typ", "JWT")
                                        .setHeaderParam("regDate", System.currentTimeMillis())
                                        .setSubject(subject)
                                        .claim(key, data)
                                        .signWith(SignatureAlgorithm.HS256, this.generateKey())
                                        .compact();
        return jwt;
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = SALT.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new UnauthorizedException();
        }
        return key;
    }

	@Override
	public Map<String, Object> get(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("Authorization");
		// System.out.println("jwt : " + jwt);
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser()
									.setSigningKey(SALT.getBytes("UTF-8"))
									.parseClaimsJws(jwt);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
		return value;
	}

	@Override
	public long getMemberMid() {
		
		return Long.valueOf((int)this.get("member").get("mid"));
	}

	@Override
	public boolean isUsable(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser()
					.setSigningKey(this.generateKey())
					.parseClaimsJws(jwt);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UnauthorizedException();
		}
	}
}