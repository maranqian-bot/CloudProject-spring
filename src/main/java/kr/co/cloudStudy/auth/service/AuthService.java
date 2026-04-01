package kr.co.cloudStudy.auth.service;

import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;
import kr.co.cloudStudy.auth.dto.ReissueRequestDTO;
import kr.co.cloudStudy.auth.dto.ReissueResponseDTO;

public interface AuthService {
	
	/**
	 * 사용자 로그인 처리 후 JWT 토큰을 발급한다.
	 * 
	 * 입력받은 사번과 비밀번호를 검증한 뒤,
	 * 인증에 성공하면 AccessToken과 RefreshToken을 생성하여 반환한다.
	 * 
	 * @param requestDTO 로그인 요청 DTO (사번, 비밀번호 포함)
	 * @return 로그인 응답 DTO (AccessToken, RefreshToken 등 포함)
	 */
	LoginResponseDTO login(LoginRequestDTO requestDTO);
	
	/**
	 * RefreshToken을 검증한 뒤 새로운 AccessToken을 재발급한다.
	 * 
	 * 클라이언트로부터 전달받은 RefreshToken이 유효한지 확인하고,
	 * 유효한 경우 새로운 AccessToken을 생성하여 반환한다.
	 * 필요에 따라 RefreshToken도 함께 재발급할 수 있다.
	 * 
	 * @param requestDTO 토큰 재발급 요청 DTO (RefreshToken 포함)
	 * @return 토큰 재발급 응답 DTO (새 AccessToken 등 포함)
	 */
	ReissueResponseDTO reissue(ReissueRequestDTO requestDTO);
	
	/**
	 * 로그아웃 처리한다.
	 * 
	 * 전달받은 RefreshToken을 저장소(DB 또는 Redis)에서 삭제하거나 무효화하여
	 * 이후 재발급 요청에 사용할 수 없도록 처리한다.
	 * 
	 * @param refreshToken 로그아웃할 사용자의 리프레시 토큰
	 */
	void logout(String refreshToken);  
}
