package kr.co.cloudStudy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "로그아웃 요청 DTO")
public class LogoutRequestDTO {

	@NotBlank(message = "refreshToken은 필수입니다.")
	@Schema(description = "refreshToken", example = "eyJhbGciOiJIUzI1NiJ9...")
	private String refreshToken;
}
