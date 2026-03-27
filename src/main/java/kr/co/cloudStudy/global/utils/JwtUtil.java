package kr.co.cloudStudy.global.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;

@Component
public class JwtUtil {
	private final Key key;
	private final long accessTokenExpiration;
	
	public JwtUtil(
			@Value("${jwt.secret}") String secretKey,
			@Value("${jwt.accress-token-expiration}") long accessTokenExpiration) {
		
		this.key = new SecretKeySpec(
				secretKey.getBytes(StandardCharsets.UTF_8),
				SignatureAlgorithm.HS256.getJcaName()
		);
		
		this.accessTokenExpiration = accessTokenExpiration;
	}
	
	public String createAccressToken(EmployeeEntity employee) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + accessTokenExpiration);
		
		return Jwts.builder()
				.subject(employee.getEmployeeNumber())
				.claim("employeeId", employee.getId())
				.claim("name", employee.getName())
				.issuedAt(now)
				.expiration(expiry)
				.signWith(key)
				.compact();
	}
	

}
