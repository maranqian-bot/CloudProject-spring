package kr.co.cloudStudy.auth.service;

/**
 * Refresh Token 관리 서비스 인터페이스
 * 
 * Redis를 이용하여 Refresh Token을 저장, 조회, 삭제하는 역할을 담당한다.
 */
public interface RefreshTokenService {
	
	/**
     * Refresh Token을 저장한다.
     * 
     * JWT의 jti 값을 key로 사용하고, 해당 토큰에 매핑되는 직원 번호를 value로 저장한다.
     * TTL(Time To Live)을 설정하여 일정 시간이 지나면 자동으로 만료되도록 한다.
     * 
     * @param jti Refresh Token의 고유 식별자 (JWT ID)
     * @param employeeNumber 직원 번호
     * @param expirationMillis 만료 시간 (밀리초 단위)
     */
	void saveRefreshToken(String jti, String employeenumber, long expirationMilllis);
	
	/**
     * Refresh Token이 존재하는지 확인한다.
     * 
     * Redis에 해당 jti 값이 존재하는지 여부를 확인한다.
     * 
     * @param jti Refresh Token의 고유 식별자 (JWT ID)
     * @return 존재 여부 (true: 존재, false: 없음)
     */
	boolean existsRefreshToken(String jti);

	/**
     * Refresh Token에 해당하는 직원 번호를 조회한다.
     * 
     * jti를 통해 Redis에 저장된 직원 번호를 조회한다.
     * 
     * @param jti Refresh Token의 고유 식별자 (JWT ID)
     * @return 직원 번호
     */
    String getEmployeeNumber(String jti);

    /**
     * Refresh Token을 삭제한다.
     * 
     * 로그아웃 시 해당 토큰을 Redis에서 제거하여 더 이상 사용할 수 없도록 한다.
     * 
     * @param jti Refresh Token의 고유 식별자 (JWT ID)
     */
    void deleteRefreshToken(String jti);
}
