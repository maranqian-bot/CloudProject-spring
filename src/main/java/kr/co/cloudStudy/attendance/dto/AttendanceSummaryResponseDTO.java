package kr.co.cloudStudy.attendance.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "근태 요약 응답 DTO")
public class AttendanceSummaryResponseDTO {
	
	@Schema(description = "이번 달 출근 일수", example = "20")
	private int workDays;
	
	@Schema(description = "지각 횟수", example = "2")
	private int lateCount;
	
	@Schema(description = "결근 횟수", example = "1")
	private int absentCount;
	
	/**
	 * 근태 점수 = 100 - (지각 * 2) - (결근 * 5)
	 */
	@Schema(description = "근태 점수 (100% 기준, 소수점 포함)", example = "92.5")
	private double attendanceScore;
	
}
