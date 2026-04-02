package kr.co.cloudStudy.department.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "부서 통계 응답 정보")
public class ResDeptStatsDTO {
	
	@Schema(description = "전체 부서 수", example = "12")
    private Long totalDepartments;

    @Schema(description = "전체 직원 수", example = "1248")
    private Long totalEmployees;

    @Schema(description = "전월 대비 성장률", example = "14.5")
    private Double growthRate;

    @Schema(description = "활성 프로젝트 수", example = "5")
    private Integer activeProjects;
    
}
