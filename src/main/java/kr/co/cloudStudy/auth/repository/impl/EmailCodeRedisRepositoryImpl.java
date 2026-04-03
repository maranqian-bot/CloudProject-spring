package kr.co.cloudStudy.auth.repository.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import kr.co.cloudStudy.auth.repository.EmailCodeRedisRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmailCodeRedisRepositoryImpl implements EmailCodeRedisRepository {
	private static final String EMAIL_CODE_PREFIX = "emailCode:";
	private static final String EMAIL_VERIFIED_PREFIX = "emailVerified:";
	private static final String EMAIL_SEND_BLOCK_PREFIX = "emailSendBlock:";
	
	private final StringRedisTemplate stringRedisTemplate;
	
	@Override
	public void saveEmailCode(String email, String code) {
		stringRedisTemplate.opsForValue().set(
				EMAIL_CODE_PREFIX + email,
				code,
				3,
				TimeUnit.MINUTES);
	}
	
	@Override
	public String getEmailCode(String email) {
		return stringRedisTemplate.opsForValue().get(EMAIL_CODE_PREFIX + email);
	}
	
	@Override
	public void deleteEmailCode(String email) {
		stringRedisTemplate.delete(EMAIL_CODE_PREFIX + email);
	}
	
	@Override
	public void saveVerified(String email) {
		stringRedisTemplate.opsForValue().set(
				EMAIL_VERIFIED_PREFIX + email,
				"true",
				10,
				TimeUnit.MINUTES
		);
	}
	
	@Override
	public boolean isVerified(String email) {
		String value = stringRedisTemplate.opsForValue().get(EMAIL_VERIFIED_PREFIX + email);
		return "true".equals(value);
	}
	
	@Override
	public void deleteVerified(String email) {
		stringRedisTemplate.delete(EMAIL_VERIFIED_PREFIX + email);
	}
	
	@Override
	public void saveSendBlock(String email) {
		stringRedisTemplate.opsForValue().set(
				EMAIL_SEND_BLOCK_PREFIX + email,
				"true",
				30,
				TimeUnit.SECONDS
		);
	}
	
	@Override
	public boolean isSendBlocked(String email) {
		return Boolean.TRUE.equals(stringRedisTemplate.hasKey(EMAIL_SEND_BLOCK_PREFIX + email));
	}
}
