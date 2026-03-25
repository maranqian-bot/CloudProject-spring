package kr.co.cloudStudy.attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.attendance.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "근태 이력 응답 DTO")
public class AttendanceHistoryResponseDTO {
	
	@Schema(description = "근태 ID", example = "1")
	private Long attendanceId;
	
	@Schema(description = "직원 ID", example = "101")
	private Long employeeId; // fk로 가져올 예정
	
	@Schema(description = "근무 날짜", example = "2026-03-25")
	private LocalDate workDate;
	
	@Schema(description = "출근 시각", example = "09:00:00")
	private LocalDateTime checkInTime;
	
	@Schema(description = "퇴근 시각", example = "18:30:00")
	private LocalDateTime checkOutTime;
	
	@Schema(description = "총 근무 시간", example = "570")
	private Integer workMinutes;
	
	@Schema(description = "근태 상태 (NORMAL: 정상, LATE: 지각, EARLYLEAVE: 조퇴, VACATION: 휴가, ABSENT: 결근, OVERTIME: 연장 근무)", 
			example = "NORMAL")
	private AttendanceStatus attendanceStatus;
	
}
