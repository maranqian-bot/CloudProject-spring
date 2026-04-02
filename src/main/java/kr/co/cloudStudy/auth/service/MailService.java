package kr.co.cloudStudy.auth.service;

public interface MailService {
	
	public void sendTestMail(String toEmail);
	
	public void sendEmailCode(String toEmail, String code);
}
