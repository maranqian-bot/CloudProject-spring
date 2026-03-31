package kr.co.cloudStudy.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.attendance.dto.AttendanceExcelRowDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDTO {

	@Schema(description = "accessToken", example = "eyJhbGciOiJIUzI1NiJ9...")
	private String accessToken;
	
	@Schema(description = "refreshToken", example = "eyJhbGciOiJIUzI1NiJ9...")
	private String refreshToken;
	
	@Schema(description = "사번", example = "EMP-2024-001")
	private String employeeNumber;
	
	@Schema(description = "이름", example = "홍길동")
	private String name;
	
}
