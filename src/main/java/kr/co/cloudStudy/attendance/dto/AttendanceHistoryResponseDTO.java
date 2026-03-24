package kr.co.cloudStudy.attendance.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kr.co.cloudStudy.attendance.entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceHistoryResponseDTO {
//	- 날짜(workDate)
//	- 출근 시각(checkInTime)
//	- 퇴근 시각(checkOutTime)
//	- 근무 시간(workMinutes)
//	- 상태(attendanceStatus): NORMAL, LATE, EARLYLEAVE, VACATION, OVERTIME
	private Long id;
	private LocalDate workDate;
	private LocalDateTime checkInTime;
	private LocalDateTime checkOutTime;
	private Integer workMinutes;
	private AttendanceStatus attendanceStatus;
	
}
