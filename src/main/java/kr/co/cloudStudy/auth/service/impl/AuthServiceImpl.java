package kr.co.cloudStudy.auth.service.impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;
import kr.co.cloudStudy.auth.dto.ReissueRequestDTO;
import kr.co.cloudStudy.auth.dto.ReissueResponseDTO;
import kr.co.cloudStudy.auth.entity.RefreshToken;
import kr.co.cloudStudy.auth.jwt.JwtUtil;
import kr.co.cloudStudy.auth.repository.RefreshTokenRepository;
import kr.co.cloudStudy.auth.service.AuthService;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final EmployeeRepository employeeRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	@Override
	public LoginResponseDTO login(LoginRequestDTO requestDTO) {
		// 1. 사전으로 직원 조회
		EmployeeEntity employee = employeeRepository.findByEmployeeNumber(requestDTO.getEmployeeNumber())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사번입니다."));
				
		// 2. 비밀번호 비교
		boolean isMatch = passwordEncoder.matches(requestDTO.getPassword(), employee.getPassword());
		
		// 3. 상태 확인
		if (!"활성".equals(employee.getStatus())) {
			throw new IllegalArgumentException("비활성화된 계정입니다.");
		}
		
		// 4. access 토큰 생성
		String accessToken = jwtUtil.createAccessToken(employee);
		
		// 5. refresh token 생성
		String refreshToken = jwtUtil.createRefreshToken(employee);
		
		// 6. refresh token 만료 시간 추출
		Date expiration = (Date) jwtUtil.getExpiration(refreshToken);
		LocalDateTime expiryDate = expiration.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		
		// 7. 기존 refresh token이 있으면 수정, 없으면 새로 저장
		//   RefreshToken
		RefreshToken savedToken = refreshTokenRepository.findByEmployeeNumber(employee.getEmployeeNumber())
															.map(existingToken -> {
																existingToken.updateToken(refreshToken, expiryDate);
																return existingToken;
															})
															.orElse(
																	RefreshToken.builder()
																	.employeeNumber(employee.getEmployeeNumber())
																	.token(refreshToken)
																	.expiryDate(expiryDate)
																	.build()
															);
		refreshTokenRepository.save(savedToken);
		
		// 7. 응답 DTO 반환
		return LoginResponseDTO.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.employeeNumber(employee.getEmployeeNumber())
				.name(employee.getName())
				.build();
	}
	
	/**
	 * 토큰 재발급
	 */
	@Override
	public ReissueResponseDTO reissue(ReissueRequestDTO requestDTO) {
		
		String refreshToken = requestDTO.getRefreshToken();
		
		// 1. refresh token 자체가 유효한지 확인
		if (!jwtUtil.validateToken(refreshToken)) {
			throw new IllegalArgumentException("유효하지 않은 refresh token입니다.");
		}
		
		// 2. refresh token 안에서 사번 꺼내기
		String employeeNumber = jwtUtil.getEmployeeNumber(refreshToken);
		
		// 3. db에 저장된 토큰 조회
		RefreshToken savedToken = refreshTokenRepository.findByEmployeeNumber(employeeNumber)
				.orElseThrow(() -> new IllegalArgumentException("저장된 refresh token이 없습니다."));
		
		// 4. 요청으로 들어온 토큰과 db 토큰이 같은지 확인
		if (!savedToken.getToken().equals(refreshToken)) {
			throw new IllegalArgumentException("refresh token이 일치하지 않습니다.");
		}
		
		// 5. 직원 정보 조회
		EmployeeEntity employee = employeeRepository.findByEmployeeNumber(employeeNumber)
				.orElseThrow(() -> new IllegalArgumentException("직원 정보를 찾을 수 없습니다."));
		
		// 6. 새 access token 생성
		String newAccessToken = jwtUtil.createAccessToken(employee);
		
		// 7. 새 refresh token도 다시 생성
		String newRefreshToken = jwtUtil.createRefreshToken(employee);
		
		// 8. 새 refresh token 만료 시간 계산
		Date expiration = (Date) jwtUtil.getExpiration(newRefreshToken);
		LocalDateTime expiryDate = expiration.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
		
		// 9. DB refresh token 갱신
		savedToken.updateToken(newRefreshToken, expiryDate);
		refreshTokenRepository.save(savedToken);
		
		// 10. 응답 반환
		return ReissueResponseDTO.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();
		
	}
	
	/**
	* 로그아웃
	*/
	@Override
	public void logout(String refreshToken) {

	   // 1. refresh token 유효성 검사
	   if (!jwtUtil.validateToken(refreshToken)) {
	       throw new IllegalArgumentException("유효하지 않은 refresh token입니다.");
	   }

	   // 2. 사번 추출
	   String employeeNumber = jwtUtil.getEmployeeNumber(refreshToken);

	   // 3. DB에서 삭제
	   refreshTokenRepository.deleteByEmployeeNumber(employeeNumber);
	}

	
	
	
}
