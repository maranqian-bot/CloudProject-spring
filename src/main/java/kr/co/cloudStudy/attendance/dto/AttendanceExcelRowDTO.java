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
@Schema(description = "근태 엑셀 한 행 데이터 DTO")
public class AttendanceExcelRowDTO {
	
	@Schema(description = "근무 날짜", example = "2026-03-25")
    private String workDate;

    @Schema(description = "출근 시각", example = "09:00")
    private String checkInTime;

    @Schema(description = "퇴근 시각", example = "18:30")
    private String checkOutTime;

    @Schema(description = "총 근무 시간 (분)", example = "570")
    private Integer workMinutes;

    @Schema(description = "근태 상태", example = "정상")
    private String attendanceStatusLabel;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

