package kr.co.cloudStudy.auth.jwt;

import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;

@Component
public class JwtUtil {
	private final SecretKey secretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;
	
	public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }
	
	/**
	 * access token 생성
	 */
	public String createAccessToken(EmployeeEntity employee) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + accessTokenExpiration);
		
		return Jwts.builder()
				.subject(employee.getEmployeeNumber())
				.claim("employeeId", employee.getId())
				.claim("name", employee.getName())
				.issuedAt(now)
				.expiration(expiry)
				.signWith(secretKey)
				.compact();
	}
	
	/**
	 * refresh token
	 */
	public String createRefreshToken(EmployeeEntity employee) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + refreshTokenExpiration);
		
		return Jwts.builder()
				.subject(employee.getEmployeeNumber())
				.issuedAt(now)
				.expiration(expiry)
				.signWith(secretKey)
				.compact();
	}
	
	/**
	 * 토큰에서 사번 추출
	 */
	public String getEmployeeNumber(String token) {
		return getClaims(token).getSubject();
	}
	
	/**
     * 토큰 만료시간 추출
     */
    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    /**
     * 토큰 유효성 검사
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Claims 꺼내는 내부 메서드
     */
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
	

}
