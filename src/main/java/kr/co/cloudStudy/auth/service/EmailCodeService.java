package kr.co.cloudStudy.auth.service;

public interface EmailCodeService {

	public void sendEmailCode(String email);
	
	public void verifyEmailCode(String email, String code);
	
	public void resetPassword(String employeeNumber, String email, String newPassword);
	
}
