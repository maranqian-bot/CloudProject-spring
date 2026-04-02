package kr.co.cloudStudy.dashboard.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.attendance.entity.Attendance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "대시보드 오늘 출근/퇴근 상태 DTO")
public class DashboardTodayAttendanceResponseDTO {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Schema(description = "근무일", example = "2026-04-02")
    private LocalDate workDate;

    @Schema(description = "출근 여부", example = "true")
    private boolean isCheckedIn;

    @Schema(description = "퇴근 여부", example = "false")
    private boolean isCheckedOut;

    @Schema(description = "출근 시간", example = "09:03")
    private String checkInTime;

    @Schema(description = "퇴근 시간", example = "18:12")
    private String checkOutTime;

    @Schema(description = "근태 기록 ID", example = "15")
    private Long historyId;

    public static DashboardTodayAttendanceResponseDTO of(LocalDate workDate, Attendance attendance) {
        if (attendance == null) {
            return DashboardTodayAttendanceResponseDTO.builder()
                    .workDate(workDate)
                    .isCheckedIn(false)
                    .isCheckedOut(false)
                    .checkInTime(null)
                    .checkOutTime(null)
                    .historyId(null)
                    .build();
        }

        return DashboardTodayAttendanceResponseDTO.builder()
                .workDate(workDate)
                .isCheckedIn(attendance.getCheckInTime() != null)
                .isCheckedOut(attendance.getCheckOutTime() != null)
                .checkInTime(attendance.getCheckInTime() != null
                        ? attendance.getCheckInTime().toLocalTime().format(TIME_FORMATTER)
                        : null)
                .checkOutTime(attendance.getCheckOutTime() != null
                        ? attendance.getCheckOutTime().toLocalTime().format(TIME_FORMATTER)
                        : null)
                .historyId(attendance.getAttendanceId())
                .build();
    }
}