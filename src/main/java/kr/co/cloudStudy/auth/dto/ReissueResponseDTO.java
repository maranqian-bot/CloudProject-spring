package kr.co.cloudStudy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "토큰 재발급 응답 DTO")
public class ReissueResponseDTO {
	
	@Schema(description = "accessToken", example = "eyJhbGciOiJIUzI1NiJ9...")
	private String accessToken;
	
	@Schema(description = "refreshToken", example = "eyJhbGciOiJIUzI1NiJ9...")
	private String refreshToken;
}
