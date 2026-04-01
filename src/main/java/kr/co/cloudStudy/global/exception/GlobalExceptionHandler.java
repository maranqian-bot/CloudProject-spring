package kr.co.cloudStudy.global.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
     * @Valid 검증 실패 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDTO.fail(HttpStatus.BAD_REQUEST, "입력값 검증에 실패했습니다.", errors));
    }

    /**
     * JSON 형식 오류, enum 오타, 날짜 파싱 실패 등
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDTO.fail(HttpStatus.BAD_REQUEST, "요청 본문 형식이 올바르지 않습니다."));
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
