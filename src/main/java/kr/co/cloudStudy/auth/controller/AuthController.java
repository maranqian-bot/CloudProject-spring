package kr.co.cloudStudy.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;
import kr.co.cloudStudy.auth.dto.LogoutRequestDTO;
import kr.co.cloudStudy.auth.dto.ReissueRequestDTO;
import kr.co.cloudStudy.auth.dto.ReissueResponseDTO;
import kr.co.cloudStudy.auth.jwt.JwtUtil;
import kr.co.cloudStudy.auth.service.AuthService;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 API")
public class AuthController {
	private final EmployeeRepository employeeRepository;
	private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
	private final AuthService authService;
	
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "사번과 비밀번호를 입력받아 JWT 토큰을 발급합니다.")
	public ResponseEntity<ApiResponseDTO<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
		
		LoginResponseDTO responseDTO = authService.login(requestDTO);
		
		return ResponseEntity.ok(ApiResponseDTO.success("로그인 성공", responseDTO));
	}
	
	@PostMapping("/reissue")
	public ResponseEntity<ApiResponseDTO<ReissueResponseDTO>> reissue(
			@Valid @RequestBody ReissueRequestDTO requestDTO
	) {
			ReissueResponseDTO responseDTO = authService.reissue(requestDTO);
			
			return ResponseEntity.ok(ApiResponseDTO.success("토큰 재발급 성공", responseDTO));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<ApiResponseDTO<Void>> logout(
			@Valid @RequestBody LogoutRequestDTO requestDTO
	) {
		authService.logout(requestDTO.getRefreshToken());
		
		return ResponseEntity.ok(ApiResponseDTO.success("로그아웃 성공", null));
	}
	
	// 테스트용 계정 생성
	@PostMapping("/test/signup")
	public String signupTest() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        EmployeeEntity employee = EmployeeEntity.builder()
                .employeeNumber("EMP-2024-002")
                .password(encoder.encode("Qwer1234!"))
                .name("홍길동")
                .deptId(1L)
                .build();

        employeeRepository.save(employee);

        return "테스트 계정 생성 완료";
	}

}
