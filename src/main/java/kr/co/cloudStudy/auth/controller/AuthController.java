package kr.co.cloudStudy.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.auth.dto.LoginRequestDTO;
import kr.co.cloudStudy.auth.dto.LoginResponseDTO;
import kr.co.cloudStudy.auth.service.AuthService;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "인증 API")
public class AuthController {
	private final AuthService authService;
	
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "사번과 비밀번호를 입력받아 JWT 토큰을 발급합니다.")
	public ResponseEntity<ApiResponseDTO<LoginResponseDTO>> login(@RequestBody LoginRequestDTO requestDTO) {
		
		LoginResponseDTO responseDTO = authService.login(requestDTO);
		
		return ResponseEntity.ok(ApiResponseDTO.success("로그인 성공", responseDTO));
	}

}
