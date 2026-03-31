package kr.co.cloudStudy.auth.service.impl;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
	
	private final StringRedisTemplate redisTemplate;
	
	private static final String PREFIX = "refresh:";
	
	@Override
	public void saveRefreshToken(String jti, String employeenumber, long expirationMilllis) {
		String key = PREFIX + jti;
		redisTemplate.opsForValue().set(key, employeenumber, expirationMilllis, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public boolean existsRefreshToken(String jti) {
		String key = PREFIX + jti;
		Boolean exists = redisTemplate.hasKey(key);
		return Boolean.TRUE.equals(exists);
	}

	@Override
    public String getEmployeeNumber(String jti) {
		String key = PREFIX + jti;
		return redisTemplate.opsForValue().get(key);
	}

	@Override
    public void deleteRefreshToken(String jti) {
		String key = PREFIX + jti;
		redisTemplate.delete(key);
	}

}
