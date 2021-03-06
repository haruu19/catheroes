package com.web.blog.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.opsworkscm.model.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.dao.MemberDao;
import com.web.blog.exception.PasswordWrongException;
import com.web.blog.model.Member;
import com.web.blog.model.request.KakaoProfileRequest;
import com.web.blog.model.response.OAuthTokenResponse;
import com.web.blog.utill.amazon.AmazonClient;
import com.web.blog.utill.jwt.JwtService;

@Service
@Transactional
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public List<Member> findAll() {
		return memberDao.findAll();
	}
	
	@Override
	public Member findMemberByMid(long mid) {
		Optional<Member> memberDb =  memberDao.findMemberByMid(mid);
		
		if(memberDb.isPresent()) {
			memberDb.get().setNickname(memberDb.get().getNickname().split("_")[0]);
			return memberDb.get();
		}else {
			throw new ResourceNotFoundException("Record not found with mid : " + mid);			
		}
	}

	@Override
	public Member updateMember(Member member) {
		Optional<Member> memberDb = memberDao.findMemberByMid(member.getMid());
		
		if(memberDb.isPresent()) {
			Member memberUpdate = memberDb.get();
			memberUpdate.setNickname(member.getNickname());
			memberUpdate.setPassword(passwordEncoder.encode(member.getPassword()));			

			memberDao.save(memberUpdate);
			return memberUpdate;
		}else {
			throw new ResourceNotFoundException("Record not found with mid : "+member.getMid());
		}
		
	}

	@Override
	public void deleteByMid(long mid) {		
		Optional<Member> memberDb = memberDao.findMemberByMid(mid);
		if(memberDb.isPresent()) {
			memberDao.delete(memberDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with mid : " + mid);			
		}
	}

	@Override
	@Transactional
	public Member saveMember(Member member) {
		String encodePassword = passwordEncoder.encode(member.getPassword());
		
		member.setPassword(encodePassword);
		member.setImage("static/profile/user/default.jpg");
		return memberDao.save(member);
	}

	@Override
	public String KakaoLogin(OAuthTokenResponse oauthToken) {		
		RestTemplate rt = new RestTemplate();
		// HttpHeader ???????????? ??????
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader??? HttpBody??? ????????? ??????????????? ??????
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

		// Http ???????????? - POST???????????? - ????????? response ????????? ?????? ??????.
		ResponseEntity<String> KakaoResponse = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest, String.class);

		ObjectMapper objectMapper = new ObjectMapper();
		KakaoProfileRequest kakaoProfile = null;
		try {
			kakaoProfile = objectMapper.readValue(KakaoResponse.getBody(), KakaoProfileRequest.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		// User ???????????? : username, password, email
		System.out.println("????????? ?????????(??????) : " + kakaoProfile.getId());
		System.out.println("????????? ????????? : " + kakaoProfile.getKakao_account().getEmail());

		System.out.println("??????????????? ???????????? : " + kakaoProfile.getKakao_account().getProfile().getNickname() + "_" + kakaoProfile.getId());
		System.out.println("??????????????? ????????? : " + kakaoProfile.getKakao_account().getEmail());
		// UUID??? -> ???????????? ?????? ?????? ?????? ?????? ??????????????? ????????????

		Member kakaoUser = Member.builder()				
				.nickname(kakaoProfile.getProperties().getNickname() + "_" + kakaoProfile.getId())
				.email(kakaoProfile.getKakao_account().getEmail()).auth("kakao").image("static/profile/user/default.jpg").build();

		// ????????? ?????? ???????????? ?????? ?????? ??????
		Optional<Member> originUser = memberDao.findMemberByEmail(kakaoUser.getEmail());

		if (!originUser.isPresent()) {
			System.out.println("?????? ????????? ???????????? ?????? ??????????????? ???????????????.");
			memberDao.save(kakaoUser);
			Optional<Member> memberDb = memberDao.findMemberByEmail(kakaoUser.getEmail());
			String token = jwtService.create("member", memberDb.get(), "member");
			System.out.println(token);
			return token;
		
		} else {// ????????????
			if (originUser.get().getAuth() == null) {
				throw new ResourceNotFoundException("?????? ???????????? ????????????. : "+ originUser.get().getEmail());
			} else if("kakao".equals(originUser.get().getAuth())){				
				// ????????? ??????
				Optional<Member> memberDb = memberDao.findMemberByEmail(kakaoUser.getEmail());
				String token = jwtService.create("member", memberDb.get(), "member");
				return token;				
			}
		}
		return null;		
	}

	//???????????? ????????? true ????????? false
	@Override
	public boolean isNickName(String nickname) {
		Optional<Member> memberDb = memberDao.findMemberByNickname(nickname);
		if(memberDb.isPresent()) {
			return true;
		}else {			
			return false;
		}
	}
	
	//???????????? ????????? true ????????? false
	@Override
	public boolean isEmail(String email) {
		Optional<Member> memberDb = memberDao.findMemberByEmail(email);		
		if(memberDb.isPresent()) {
			return true;
		}else {			
			return false;
		}
	}

	@Override
	public String Login(String email, String password) {
		Optional<Member> memberDb = memberDao.findMemberByEmail(email);
		if(memberDb.isPresent()) {	
			if(passwordEncoder.matches(password, memberDb.get().getPassword())){
				return jwtService.create("member", memberDb.get(), "member");				
			}else {
				throw new PasswordWrongException();				
			}

		}else {
			throw new ResourceNotFoundException("Record not equals email,password : "+email);
		}
	}

	@Override
	public String updateImageById(MultipartFile image,Long mid) throws Exception {
		Optional<Member> memberDb = memberDao.findMemberByMid(mid);
		String path=null;
		if(memberDb.isPresent()) {
			try {
				path = this.amazonClient.uploadFile(image, mid, "profile/user/");
				memberDao.updateImage(mid, path);		
				System.out.println("############");
				System.out.println(path);								
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}else {			
			throw new ResourceNotFoundException("Record not found with mid : " + mid);		
		}		
		return path;
	}

	@Override
	public String updateNewsfeedById(Long cid, Long mid) throws Exception {
		// Optional<Member> memberDb = memberDao.findMemberByMid(mid);
		// String data = "";
		// if(memberDb.isPresent()) {
		// 	try {
				memberDao.updateNews(cid, mid);
		// 		data = "success";
		// 	} catch (Exception e) {
		// 		// TODO Auto-generated catch block
		// 		e.printStackTrace();
		// 	}			
		// }else {			
		// 	throw new ResourceNotFoundException("Record not found with mid : " + mid);		
		// }		
		return null;
	}

	@Override
	public Member clearAlarm(Member member) {
		Optional<Member> memberDb = memberDao.findMemberByMid(member.getMid());
		
		if(memberDb.isPresent()) {
			Member memberUpdate = memberDb.get();
			memberUpdate.setNews(member.getNews());
			
			memberDao.save(memberUpdate);
			return memberUpdate;
		}else {
			throw new ResourceNotFoundException("Record not found with mid : "+member.getMid());
		}
	}
}
