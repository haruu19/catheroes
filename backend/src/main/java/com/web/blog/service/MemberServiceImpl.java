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
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

		// Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
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

		// User 오브젝트 : username, password, email
		System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
		System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());

		System.out.println("블로그서버 유저네임 : " + kakaoProfile.getKakao_account().getProfile().getNickname() + "_" + kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘

		Member kakaoUser = Member.builder()				
				.nickname(kakaoProfile.getProperties().getNickname() + "_" + kakaoProfile.getId())
				.email(kakaoProfile.getKakao_account().getEmail()).auth("kakao").image("static/profile/user/default.jpg").build();

		// 가입자 혹은 비가입자 체크 해서 처리
		Optional<Member> originUser = memberDao.findMemberByEmail(kakaoUser.getEmail());

		if (!originUser.isPresent()) {
			System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
			memberDao.save(kakaoUser);
			Optional<Member> memberDb = memberDao.findMemberByEmail(kakaoUser.getEmail());
			String token = jwtService.create("member", memberDb.get(), "member");
			System.out.println(token);
			return token;
		
		} else {// 기존유저
			if (originUser.get().getAuth() == null) {
				throw new ResourceNotFoundException("이미 가입되어 있습니다. : "+ originUser.get().getEmail());
			} else if("kakao".equals(originUser.get().getAuth())){				
				// 로그인 처리
				Optional<Member> memberDb = memberDao.findMemberByEmail(kakaoUser.getEmail());
				String token = jwtService.create("member", memberDb.get(), "member");
				return token;				
			}
		}
		return null;		
	}

	//닉네임이 있으면 true 없으면 false
	@Override
	public boolean isNickName(String nickname) {
		Optional<Member> memberDb = memberDao.findMemberByNickname(nickname);
		if(memberDb.isPresent()) {
			return true;
		}else {			
			return false;
		}
	}
	
	//이메일이 있으면 true 없으면 false
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
