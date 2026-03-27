package kr.co.cloudStudy.auth.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;
import kr.co.cloudStudy.auth.service.AuthService;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import kr.co.cloudStudy.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	private final EmployeeRepository employeeRepository;
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
		
		// 4. JWT 토큰 생성
		String accessToken = jwtUtil.createAccressToken(employee);
		
		// 5. 응답 DTO 반환
		return LoginResponseDTO.builder()
				.accessToken(accessToken)
				.employeeNumber(employee.getEmployeeNumber())
				.name(employee.getName())
				.build();
	}
}
