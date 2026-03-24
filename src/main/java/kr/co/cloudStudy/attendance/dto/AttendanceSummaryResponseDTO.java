package kr.co.cloudStudy.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceSummaryResponseDTO {
//	- 이번 달 출근 일수(workDays)
//	- 지각 횟수(lateCount)
//	- 총 결근 일수(absentCount)
//	- 근태 점수(attendanceScore)
	private int workDays;
	private int lateCount;
	private int absentCount;
	private double attendanceScore;
	
}
