package kr.co.cloudStudy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueRequestDTO {

	@NotBlank(message = "refreshToken은 필수입니다.")
	@Schema(description = "refresh토큰")
	private String refreshToken;
}
