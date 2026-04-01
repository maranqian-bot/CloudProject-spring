package kr.co.cloudStudy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로그인 요청 dto")
public class LoginRequestDTO {
	
	@NotBlank(message = "사번은 필수입니다.")
	@Schema(description = "사번", example = "EMP-2024-001")
	private String employeeNumber;
	
	@NotBlank(message = "비밀번호는 필수입니다.")
	@Schema(description = "비밀번호", example = "Qwer1234!")
	private String password;
	
}
