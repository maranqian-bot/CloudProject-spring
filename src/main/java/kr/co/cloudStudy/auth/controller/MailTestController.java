package kr.co.cloudStudy.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.cloudStudy.auth.dto.MailTestRequestDTO;
import kr.co.cloudStudy.auth.service.MailService;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MailTestController {
	
	private final MailService mailService;
	
	@PostMapping("/api/auth/mail-test")
	public ResponseEntity<ApiResponseDTO<Void>> sendMailTest(
			@Valid @RequestBody MailTestRequestDTO requestDTO
	) {
		mailService.sendTestMail(requestDTO.getEmail());
		
		return ResponseEntity.ok(ApiResponseDTO.success(HttpStatus.OK, "테스트 메일 발송에 성공했습니다.", null));
	}

}
