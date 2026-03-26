package kr.co.cloudStudy.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.cloudStudy.global.dto.ApiResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	/**
	 * 잘못된 요청값 들어왔을 때
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiResponseDTO<?>> handleIllegalArgumentException(IllegalArgumentException e) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponseDTO.fail(HttpStatus.BAD_REQUEST, e.getMessage()));
	}
	
	/**
	 * 데이터를 찾을 수 없을 때 처리
	 */
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ApiResponseDTO<?>> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ApiResponseDTO.fail(HttpStatus.NOT_FOUND, e.getMessage()));
	}
	
	/**
	 * 그 외 모든 예외 처리
	 */
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<?>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDTO.fail(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."));
    }
}
