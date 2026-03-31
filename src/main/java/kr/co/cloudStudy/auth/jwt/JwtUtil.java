package kr.co.cloudStudy.auth.jwt;

import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

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
	 * Access token 생성
	 */
	public String createAccessToken(EmployeeEntity employee) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + accessTokenExpiration);
		
		return Jwts.builder()
				.subject(employee.getEmployeeNumber())
				.claim("type", "access")
				.claim("employeeId", employee.getId())
				.claim("name", employee.getName())
				.issuedAt(now)
				.expiration(expiry)
				.signWith(secretKey)
				.compact();
	}
	
	/**
	 * Refresh token
	 */
	public String createRefreshToken(EmployeeEntity employee) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + refreshTokenExpiration);
		
		return Jwts.builder()
				.subject(employee.getEmployeeNumber())
				.claim("type", "refresh")
				.claim("jti", UUID.randomUUID().toString())
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
	 * 토큰에서 타입 추출
	 */
	public String getTokenType(String token) {
		return getClaims(token).get("type", String.class);
	}
	
	/**
	 * 토큰에서 jti 추출
	 */
	public String getJti(String token) {
		return getClaims(token).get("jti", String.class);
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
        	System.out.println("JWT 검증 실패: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Access Token인지 확인
     */
    public boolean isAccessToken(String token) {
    	try {
    		String type = getTokenType(token);
    		return "access".equals(type);
    	} catch (Exception e) {
    		return false;
    	}
    }
    
    /**
     * Refresh Token인지 확인
     */
    public boolean isRefreshToken(String token) {
    	try {
    		String type = getTokenType(token);
    		return "refresh".equals(type);
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
