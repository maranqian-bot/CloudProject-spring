package kr.co.cloudStudy.auth.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmailCodeRedisRepository {
	
	private static final String EMAIL_CODE_PREFIX = "emailCode:";
	private static final String EMAIL_VERIFIED_PREFIX = "emailVerified:";
	
	private final StringRedisTemplate stringRedisTemplate;
	
	public void saveEmailCode(String email, String code) {
		stringRedisTemplate.opsForValue().set(
				EMAIL_CODE_PREFIX + email,
				code,
				3,
				TimeUnit.MINUTES);
	}
	
	public String getEmailCode(String email) {
		return stringRedisTemplate.opsForValue().get(EMAIL_CODE_PREFIX + email);
	}
	
	public void deleteEmailCode(String email) {
		stringRedisTemplate.delete(EMAIL_CODE_PREFIX + email);
	}
	
	public void saveVerified(String email) {
		stringRedisTemplate.opsForValue().set(
				EMAIL_VERIFIED_PREFIX + email,
				"true",
				10,
				TimeUnit.MINUTES
		);
	}
	
	public boolean isVerifired(String email) {
		String value = stringRedisTemplate.opsForValue().get(EMAIL_VERIFIED_PREFIX + email);
		return "true".equals(value);
	}
	
	public void deleteVerified(String email) {
		stringRedisTemplate.delete(EMAIL_VERIFIED_PREFIX + email);
	}
}
