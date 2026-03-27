package kr.co.cloudStudy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReissueResponseDTO {
	
	@Schema(description = "access토큰")
	private String accessToken;
	
	@Schema(description = "refresh토큰")
	private String refreshToken;
}
