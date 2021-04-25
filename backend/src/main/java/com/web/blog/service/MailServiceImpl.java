package com.web.blog.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	public String send(String subject, String to,String text) {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		try {
			//이메일 인증
			int ran = new Random().nextInt(900000) + 100000;
			
			String authCode = String.valueOf(ran);
			
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
			helper.setSubject(subject);
			helper.setText(new StringBuilder().append("<p style=\"font-size:10pt;font-family:sans-serif;padding:0 0 0 10pt\"><br>&nbsp;</p>")
               .append("<p>")
               .append(" <xmeta charset=\"utf-8\"></xmeta>")
               .append("</p>")
               .append("<table align=\"center\" width=\"670\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-top: 2px solid #60b9ce; border-right: 1px solid #e7e7e7; border-left: 1px solid #e7e7e7; border-bottom: 1px solid #e7e7e7;\">\r\n") 
               .append("       <tbody><tr><td style=\"background-color: #ffffff; padding: 40px 30px 0 35px; text-align: center;\">") 
               .append("             <table width=\"605\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"text-align: left; font-family: '맑은 고딕','돋움';\">")  
               .append("                <tbody><tr><td style=\"color: #2daad1; font-size: 25px; text-align: left; width: 352px; word-spacing: -6px; vertical-align: top;\">") 
               .append("                   길냥이 히어로즈<br>\r\n")
               .append(text)
               .append("                   </td><td rowspan=\"3\"><img src=\"https://catheroes.s3.ap-northeast-2.amazonaws.com/static/profile/user/default.jpg\" loading=\"lazy\"></td></tr>") 
               .append("                <tr><td height=\"39\"><img src=\"http://neowiz.recruiter.co.kr/resources/css/cus/images/pattern/registerbar.png\" loading=\"lazy\"></td></tr>") 
               .append("                <tr><td style=\"font-size: 17px; vertical-align: bottom; height: 27px;\">안녕하세요? 길냥이 히어로즈 입니다.</td></tr>") 
               .append("                <tr><td colspan=\"2\" style=\"font-size: 13px; word-spacing: -1px; height: 30px;\">아래 인증번호를 입력하여 인증 하시면 됩니다.</td></tr></tbody></table>")
               .append("          </td></tr>") 
               .append("       <tr><td style=\"padding: 39px 196px 70px;\">") 
               .append("             <table width=\"278\" style=\"background-color: #3cbfaf; font-family: '맑은 고딕','돋움';\">") 
               .append("                <tbody><tr><td height=\"49\" style=\"text-align: center; color: #fff\">인증번호 : <span>")
               .append(authCode)
               .append("</span></td></tr>") 
               .append("             </tbody></table>") 
               .append("          </td></tr>") 
               .append("    </tbody></table>") 
               .append(" <p></p>") 
               .append("<img height=\"1\" width=\"1\" border=\"0\" style=\"display:none;\" src=\"http://ems.midasit.com:4121/9I-110048I-41E-822811815I-4uPmuPzeI-4I-3\" loading=\"lazy\">") 
               .toString(),true);
			helper.setFrom("ssafycatheroes@gmail.com");
			helper.setTo(to);
			
			javaMailSender.send(message);
			return authCode;
		}catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}
	 



}
