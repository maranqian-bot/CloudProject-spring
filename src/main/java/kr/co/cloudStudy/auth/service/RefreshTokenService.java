package kr.co.cloudStudy.auth.service;

public interface RefreshTokenService {
	void saveRefreshToken(String jti, String employeenumber, long expirationMilllis);
	
	boolean existsRefreshToken(String jti);

    String getEmployeeNumber(String jti);

    void deleteRefreshToken(String jti);
}
