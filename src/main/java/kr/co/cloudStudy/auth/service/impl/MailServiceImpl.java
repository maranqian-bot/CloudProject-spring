package kr.co.cloudStudy.auth.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import kr.co.cloudStudy.auth.service.MailService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
	
	private final JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Override
	public void sendTestMail(String toEmail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject("[CloudStudy] 메일 발송 테스트");
		message.setText("안녕하세요. CloudStudy 메일 발송 테스트입니다.");
		
		javaMailSender.send(message);
	}
	
	@Override
	public void sendEmailCode(String toEmail, String code) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(fromEmail);
		message.setTo(toEmail);
		message.setSubject("[CloudStudy] 이메일 인증코드 안내");
		message.setText(
				"안녕하세요.\n\n" +
				"인증코드는 아래와 같습니다.\n\n" +
				"인증코드: " + code + "\n\n" + 
				"해당 인증코드는 3분 후 만료됩니다."
		);
		
		javaMailSender.send(message);
	}
}
