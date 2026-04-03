package kr.co.cloudStudy.auth.repository;

public interface EmailCodeRedisRepository {
	
	void saveEmailCode(String email, String code);
	
	String getEmailCode(String email);
	
	void deleteEmailCode(String email);

	void saveVerified(String email);
	
	boolean isVerified(String email);
	
	void deleteVerified(String email);

	void saveSendBlock(String email);
	
	boolean isSendBlocked(String email);
	
}
