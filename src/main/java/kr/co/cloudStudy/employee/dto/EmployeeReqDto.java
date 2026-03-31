package kr.co.cloudStudy.employee.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 직원 추가 요청처리
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Schema(description = "직원 추가 요청(입력란)필드")
public class EmployeeReqDto {
	@Schema(description = "직원 사번", example = "20240001")
	private String employeeNumber; 	// 직원사번
	@Schema(description = "이름", example = "홍길동")
	private String name;		   	// 이름
	@Schema(description = "소속 부서명 (화면 표시용)", example = "개발팀")
	private String deptName;	   	// 부서명
	@Schema(description = "직책", example = "사원") 
	private String position;	   	// 직책
	@Schema(description = "이메일 주소", example = "test@cloudstudy.co.kr")
	private String email;		   	// 이메일
	@Schema(description = "재직 상태", example = "활성")
	private String status;		   	// 상태
	@Schema(description = "비밀번호", example = "password123!")
	private String password;	   	// 비밀번호
	@Schema(description = "소속 부서 ID (PK)", example = "1")
	private Long departmentId;	   	// 부서Id
	@Schema(description = "시스템 권한", example = "ROLE_USER")
	private String role;			// 시스템 역할
	
	@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;		// 입사일 
	
}




