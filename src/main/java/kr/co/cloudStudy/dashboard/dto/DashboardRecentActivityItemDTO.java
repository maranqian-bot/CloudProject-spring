package kr.co.cloudStudy.dashboard.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.attendance.entity.Attendance;
import kr.co.cloudStudy.attendance.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "대시보드 최근 활동 항목 DTO")
public class DashboardRecentActivityItemDTO {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Schema(description = "근태 기록 ID", example = "15")
    private Long id;

    @Schema(description = "근무일", example = "2026-04-02")
    private LocalDate workDate;

    @Schema(description = "출근 시간", example = "09:03")
    private String checkInTime;

    @Schema(description = "퇴근 시간", example = "18:12")
    private String checkOutTime;

    @Schema(description = "근무 시간(분)", example = "549")
    private Integer workMinutes;

    @Schema(description = "근태 상태", example = "OVER_TIME")
    private AttendanceStatus attendanceStatus;

    public static DashboardRecentActivityItemDTO from(Attendance attendance) {
        return DashboardRecentActivityItemDTO.builder()
                .id(attendance.getAttendanceId())
                .workDate(attendance.getWorkDate())
                .checkInTime(attendance.getCheckInTime() != null
                        ? attendance.getCheckInTime().toLocalTime().format(TIME_FORMATTER)
                        : null)
                .checkOutTime(attendance.getCheckOutTime() != null
                        ? attendance.getCheckOutTime().toLocalTime().format(TIME_FORMATTER)
                        : null)
                .workMinutes(attendance.getWorkMinutes())
                .attendanceStatus(attendance.getAttendanceStatus())
                .build();
    }
}