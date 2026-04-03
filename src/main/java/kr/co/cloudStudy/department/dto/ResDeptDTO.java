package kr.co.cloudStudy.department.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//부서 조회용 DTO
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "부서 상세 응답 정보")
public class ResDeptDTO {
	
	@Schema(description = "부서 ID (PK)", example = "1")
	private Long departmentId;        // 부서 PK
	
	@Schema(description = "부서 코드", example = "DEV-001")
	private String deptCode;			 // 부서 코드
	
	@Schema(description = "부서 명", example = "개발팀")
	private String deptName;			 // 부서 이름
	
	@Schema(description = "부서 설명", example = "스프링 백엔드 개발 부서")
	private String description;			 // 부서 설명	
	
	// 추가 : 관리자 상세 정보
	@Schema(description = "관리자 사번", example = "EMP-2024-001")
	private String managerId;	
	@Schema(description = "관리자 이름", example = "김철수")
	private String managerName;      	
	@Schema(description = "관리자 직책", example = "부장")
    private String managerJobTitle;
	
	// 추가 : 부서 통계
	@Schema(description = "부서 소속 인원 수", example = "5")
	private Integer employeeCount;
	
	@Schema(description = "생성 일시", example = "2026-03-25T14:00:00")
	private LocalDateTime createdAt;     // 생성일
	
	@Schema(description = "수정 일시", example = "2026-03-25T15:30:00")
    private LocalDateTime updatedAt;
	
}
