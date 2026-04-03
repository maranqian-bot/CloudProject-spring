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
@Schema(description = "이메일 인증코드 검증 요청 DTO")
public class EmailCodeVerifyRequestDTO {
	
	@Schema(description = "이메일 주소", example = "test@gmail.com")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수입니다.")
	private String email;
	
	@Schema(description = "이메일 인증코드", example = "123456")
	@NotBlank(message = "인증코드는 필수입니다.")
	private String code;

}
