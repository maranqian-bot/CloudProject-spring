package kr.co.cloudStudy.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutRequestDTO {

	@NotBlank(message = "refreshToken은 필수입니다.")
	private String refreshToken;
}
