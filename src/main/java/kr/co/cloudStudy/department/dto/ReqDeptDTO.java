package kr.co.cloudStudy.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
		private Long deptid;
	
		@Setter
		@Schema(description = "부서 코드", example = "DEV-001", requiredMode = Schema.RequiredMode.REQUIRED)	
		private String deptCode;			 // 부서 코드 		(requiredMode = 스웨거 빨간색 별표 표시)
		
		@Setter
		@Schema(description = "부서 명", example = "개발팀", requiredMode = Schema.RequiredMode.REQUIRED)
		private String deptName;			 // 부서 이름
		
		@Setter
		@Schema(description = "부서 관리자 ID", example = "101")
		private Long managerId;
		
		@Setter
		@Schema(description = "부서 설명", example = "스프링 백엔드 개발 부서")
		private String description;			 // 부서 설명
			
}
