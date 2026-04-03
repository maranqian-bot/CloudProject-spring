package kr.co.cloudStudy.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//부서 등록 요청용 DTO
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "부서 등록 요청 정보")
public class ReqDeptDTO {
		
		@Setter
		@Schema(description = "부서 ID (수정 시 필수)", example = "1")
		private Long departmentId;
	
		@Setter
		@NotBlank(message = "부서 코드는 필수 입력 항목입니다.") // 공백/null 차단
		@Schema(description = "부서 코드", example = "DEV-001", requiredMode = Schema.RequiredMode.REQUIRED)	
		private String deptCode;			 // 부서 코드 		(requiredMode = 스웨거 빨간색 별표 표시)
		
		@Setter
		@NotBlank(message = "부서명은 필수 입력 항목입니다.")
		@Size(min = 2, max = 20, message = "부서명은 2자 이상 20자 이내로 입력해주세요.") // 길이 제한
		@Schema(description = "부서 명", example = "개발팀", requiredMode = Schema.RequiredMode.REQUIRED)
		private String deptName;			 // 부서 이름
		
		@Setter
		@NotBlank(message = "부서장 이름은 필수 입력 항목입니다.") 
		@Schema(description = "부서장 이름", example = "김철수", requiredMode = Schema.RequiredMode.REQUIRED)
		private String managerName; 
			
		@Setter
		@Schema(description = "매니저 사번", example = "EMP-2024-001", requiredMode = Schema.RequiredMode.REQUIRED)
		private String managerId;
		
		@Setter
		@Schema(description = "부서 설명", example = "스프링 백엔드 개발 부서")
		private String description;			 // 부서 설명
			
}
