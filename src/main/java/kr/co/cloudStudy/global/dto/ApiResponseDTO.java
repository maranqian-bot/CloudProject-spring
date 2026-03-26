package kr.co.cloudStudy.global.dto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공통 응답 DTO")
public class ApiResponseDTO<T> {
	
	@Schema(description = "요청 성공 여부", example = "true")
	private boolean success;
	
	@Schema(description = "HTTP 상태 코드", example = "200")
	private Integer status;
	
	@Schema(description = "응답 메시지", example = "조회 성공")
	private String message;
	
	@Schema(description = "실제 응답 데이터")
	private T data;
	
	// 성공 응답
	public static <T> ApiResponseDTO<T> success(T data) {
		return new ApiResponseDTO<>(true, HttpStatus.OK.value(), "요청 성공", data);
	}
	
	// 성공 응답 (메시지 커스텀)
	public static <T> ApiResponseDTO<T> success(String message, T data) {
		return new ApiResponseDTO<>(true, HttpStatus.OK.value(), message, data);
	}
	
	// 실패 응답
	public static <T> ApiResponseDTO<T> fail(HttpStatus status, String message) {
        return new ApiResponseDTO<>(false, status.value(), message, null);
    } 
}
