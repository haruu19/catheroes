

package com.web.blog.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.blog.model.Member;
import com.web.blog.model.response.BasicResponse;
import com.web.blog.model.response.OAuthTokenResponse;
import com.web.blog.service.MailService;
import com.web.blog.service.MemberService;
import com.web.blog.utill.jwt.JwtService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@RequestMapping("api/v1")
@RestController
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private MailService mailService;
	
	@GetMapping("/member/")
	@ApiOperation(value = "회원 전체 조회")
	public ResponseEntity<List<Member>> getAllMember(){
		return ResponseEntity.ok().body(memberService.findAll());
	}
	
	@PostMapping("/member/info")
	@ApiOperation(value = "토큰으로 회원 검색")
	public ResponseEntity<Member> getMemberByToken(){
		return ResponseEntity.ok().body(memberService.findMemberByMid(jwtService.getMemberMid()));
	}

	@PostMapping("/member")
	@ApiOperation(value = "회원 등록")
	public ResponseEntity<Member> saveMember(@RequestBody Member member){
		return ResponseEntity.ok().body(memberService.saveMember(member));
	}
	
	@PutMapping("/member")
	@ApiOperation(value = "회원 수정")
	public ResponseEntity<Member> updateMember(@RequestParam Map<String,String> request) {
		ObjectMapper objectMapper = new ObjectMapper();	
		Member member = objectMapper.convertValue(request, Member.class);
		
		return ResponseEntity.ok().body(memberService.updateMember(member));
	}
	
	@DeleteMapping("/member/{mid}")
	@ApiOperation(value = "회원 탈퇴")
	public HttpStatus deleteMember(@PathVariable long mid) {
		memberService.deleteByMid(mid);
		return HttpStatus.OK;
	}

	@PostMapping("/member/{mid}")
	@ApiOperation(value = "회원번호로 회원 검색")
	public ResponseEntity<Member> getMemberById(@PathVariable long mid){
		return ResponseEntity.ok().body(memberService.findMemberByMid(mid));
	}
	
	@PostMapping("/member/login")
	@ApiOperation(value = "로그인")
	public ResponseEntity<String> Login(@RequestParam("email") String email,@RequestParam("password") String password ){
		return ResponseEntity.ok().body(memberService.Login(email,password));
	}
	
	@PostMapping("/auth/kakao/Login")
	@ApiOperation(value = "카카오 로그인")
	public ResponseEntity<String> kakaoCallback(@RequestBody OAuthTokenResponse oauthToken) {// Data를 리턴해주는 컨트롤러
		return ResponseEntity.ok().body(memberService.KakaoLogin(oauthToken));
	}

	@PostMapping("/member/image")
	@ApiOperation(value = "사용자 이미지 변경")
	public ResponseEntity<String> updateImage(@RequestParam("image") MultipartFile image, @RequestParam("mid") Long mid) throws Exception{// Data를 리턴해주는 컨트롤러
		return ResponseEntity.ok().body(memberService.updateImageById(image, mid));
	}
	
	@PostMapping("/member/nickname/")
	@ApiOperation(value = "닉네임 중복 검사")
	public ResponseEntity<Boolean> isNickname(@RequestParam("nickname") String nickname){
		System.out.println(nickname);
		return ResponseEntity.ok().body(memberService.isNickName(nickname));
	}
	
	@PostMapping("/member/email/")
	@ApiOperation(value = "이메일 중복 검사")
	public ResponseEntity<Boolean> isEmail(@RequestParam("email") String email){
		return ResponseEntity.ok().body(memberService.isEmail(email));
	}

	@PostMapping("/member/newsfeed")
	@ApiOperation(value = "게시글 뉴스피드")
	public ResponseEntity<String> updateNewsfeed(@RequestParam("cid") Long cid, @RequestParam("mid") Long mid) throws Exception{// Data를 리턴해주는 컨트롤러
		return ResponseEntity.ok().body(memberService.updateNewsfeedById(cid, mid));
	}
	
	@PutMapping("/member/clear")
	@ApiOperation(value = "푸쉬알림 카운트 초기화")
	public ResponseEntity<Member> clearAlarm(@RequestParam Map<String,String> request) {
		ObjectMapper objectMapper = new ObjectMapper();	
		Member member = objectMapper.convertValue(request, Member.class);
		
		return ResponseEntity.ok().body(memberService.clearAlarm(member));
	}
	
	@GetMapping("/member/email")
	@ApiOperation(value = "이메일 인증 전송")
	public ResponseEntity<String> createEmailCheck(@RequestParam String userEmail,@RequestParam String subject,@RequestParam String text){
		return ResponseEntity.ok().body(mailService.send(subject,userEmail,text));		
	}
}
