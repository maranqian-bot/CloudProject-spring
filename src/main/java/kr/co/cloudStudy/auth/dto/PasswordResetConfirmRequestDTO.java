package kr.co.cloudStudy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "비밀번호 재설정 요청 DTO")
public class PasswordResetConfirmRequestDTO {
	
	@Schema(description = "사번", example = "EMP-2024-001")
	@NotBlank(message = "사번은 필수입니다.")
	private String employeeNumber;
	
	@Schema(description = "이메일 주소", example = "test@gmail.com")
	@Email(message = "올바른 이메일형식이어야 합니다.")
	@NotBlank(message = "이메일은 필수입니다.")
	private String email;
	
	@Schema(description = "새 비밀번호", example = "newPassword123!")
	@NotBlank(message = "새 비밀번호는 필수입니다.")
	private String newPassword;
}
