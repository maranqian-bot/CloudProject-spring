package kr.co.cloudStudy.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.cloudStudy.auth.dto.EmailCodeSendRequestDTO;
import kr.co.cloudStudy.auth.dto.EmailCodeVerifyRequestDTO;
import kr.co.cloudStudy.auth.dto.PasswordResetConfirmRequestDTO;
import kr.co.cloudStudy.auth.service.EmailCodeService;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/email")
public class EmailCodeController {

	private final EmailCodeService emailCodeService;
	
	@PostMapping("/send-code")
	public ResponseEntity<ApiResponseDTO<Void>> sendEmailCode(
			@Valid @RequestBody EmailCodeSendRequestDTO requestDTO
	) {
		emailCodeService.sendEmailCode(requestDTO.getEmail());
		
		return ResponseEntity.ok(ApiResponseDTO.success(HttpStatus.OK, "인증코드 발송에 성공했습니다.", null));
	}
	
	@PostMapping("/verify-code")
	public ResponseEntity<ApiResponseDTO<Void>> verifyEmailCode(
			@Valid @RequestBody EmailCodeVerifyRequestDTO requestDTO	
	) {
		emailCodeService.verifyEmailCode(requestDTO.getEmail(), requestDTO.getCode());
		
		return ResponseEntity.ok(ApiResponseDTO.success(HttpStatus.OK, "이메일 인증이 완료되었습니다.", null));
	}
	
	@PostMapping("/password-reset/confirm")
	public ResponseEntity<ApiResponseDTO<Void>> resetPassword(
			@Valid @RequestBody PasswordResetConfirmRequestDTO requestDTO
	) {
		emailCodeService.resetPassword(
				requestDTO.getEmployeeNumber(),
				requestDTO.getEmail(),
				requestDTO.getNewPassword()
		);
		return ResponseEntity.ok(ApiResponseDTO.success(HttpStatus.OK, "비밀번호가 성공적으로 변경되었습니다.", null));
	}
}
