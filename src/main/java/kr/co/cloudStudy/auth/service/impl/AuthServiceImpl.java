package kr.co.cloudStudy.auth.service.impl;

import java.util.Date;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;
import kr.co.cloudStudy.auth.dto.ReissueRequestDTO;
import kr.co.cloudStudy.auth.dto.ReissueResponseDTO;
import kr.co.cloudStudy.auth.jwt.JwtUtil;
import kr.co.cloudStudy.auth.service.AuthService;
import kr.co.cloudStudy.auth.service.RefreshTokenService;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final EmployeeRepository employeeRepository;
	private final RefreshTokenService refreshTokenService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	@Override
	public LoginResponseDTO login(LoginRequestDTO requestDTO) {

		Employee employee = employeeRepository
				.findByEmployeeNumber(requestDTO.getEmployeeNumber())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사번입니다."));
				
		// TODO: 직원 추가 기능 완료될 시 주석 풀 예정(암호화된 비번필요)
//		boolean isMatch = passwordEncoder.matches(requestDTO.getPassword(), employee.getPassword());
//		
//		if (!isMatch) {
//			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//		}
		
//		if (!"활성".equals(employee.getStatus())) {
//			throw new IllegalArgumentException("비활성화된 계정입니다.");
//		}
		
		String accessToken = jwtUtil.createAccessToken(employee);		
		String refreshToken = jwtUtil.createRefreshToken(employee);
		
		String jti = jwtUtil.getJti(refreshToken);
		
		Date expiration = jwtUtil.getExpiration(refreshToken);
		long ttl = expiration.getTime() - System.currentTimeMillis();
		
		refreshTokenService.saveRefreshToken(jti, employee.getEmployeeNumber(), ttl);
		
		return LoginResponseDTO.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.employeeId(employee.getEmployeeId())
				.employeeNumber(employee.getEmployeeNumber())
				.name(employee.getName())
				.build();
	}
	
	@Override
	public ReissueResponseDTO reissue(ReissueRequestDTO requestDTO) {
		
		String refreshToken = requestDTO.getRefreshToken();
		
		if (!jwtUtil.validateToken(refreshToken)) {
			throw new IllegalArgumentException("유효하지 않은 refresh token입니다.");
		}
		
		if (!jwtUtil.isRefreshToken(refreshToken)) {
			throw new IllegalArgumentException("refresh token이 아닙니다.");
		}
		
		String jti = jwtUtil.getJti(refreshToken);
		
		if (!refreshTokenService.existsRefreshToken(jti)) {
			throw new IllegalArgumentException("저장된 refresh token이 없습니다.");
		}
		
		String employeeNumber = refreshTokenService.getEmployeeNumber(jti);
		
		if (employeeNumber == null) {
			throw new IllegalArgumentException("저장된 직원 정보가 없습니다.");
		}
		
		Employee employee = employeeRepository.findByEmployeeNumber(employeeNumber)
				.orElseThrow(() -> new IllegalArgumentException("직원 정보를 찾을 수 없습니다."));
		
		String newAccessToken = jwtUtil.createAccessToken(employee);
		
		String newRefreshToken = jwtUtil.createRefreshToken(employee);
		
		refreshTokenService.deleteRefreshToken(jti);
		
		String newJti = jwtUtil.getJti(newRefreshToken);
		
		Date expiration = jwtUtil.getExpiration(newRefreshToken);
		long ttl = expiration.getTime() - System.currentTimeMillis();
		
		refreshTokenService.saveRefreshToken(newJti, employee.getEmployeeNumber(), ttl);
		
		return ReissueResponseDTO.builder()
				.accessToken(newAccessToken)
				.refreshToken(newRefreshToken)
				.build();
		
	}
	
	@Override
	public void logout(String refreshToken) {

	   if (!jwtUtil.validateToken(refreshToken)) {
	       throw new IllegalArgumentException("유효하지 않은 refresh token입니다.");
	   }
	   
	   if (!jwtUtil.isRefreshToken(refreshToken)) {
			throw new IllegalArgumentException("refresh token이 아닙니다.");
	   }
		
	   String jti = jwtUtil.getJti(refreshToken);

	   if (!refreshTokenService.existsRefreshToken(jti)) {
		   throw new IllegalArgumentException("이미 로그아웃되었거나 저장되지 않은 refresh token입니다.");
	   }
	   
	   refreshTokenService.deleteRefreshToken(jti);
	}

	
	
	
}
