package kr.co.cloudStudy.auth.service;

public interface EmailCodeRedisService {

	public void saveEmailCode(String email, String code);
	
	public String getEmailCode(String email);
	
	public void deleteEmailCode(String email);
	
	public void saveVerified(String email);
	
	public boolean isVerifired(String email);
	
	public void deleteVerified(String email);
}
