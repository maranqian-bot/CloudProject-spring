package kr.co.cloudStudy.dashboard.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "대시보드 휴가 요청 요약 DTO")
public class DashboardVacationRequestDTO {

    @Schema(description = "휴가 신청 ID", example = "21")
    private Long id;

    @Schema(description = "직원 ID", example = "3")
    private Long employeeId;

    @Schema(description = "휴가 시작일", example = "2026-04-10")
    private LocalDate startDate;

    @Schema(description = "휴가 일수", example = "1")
    private BigDecimal days;

    @Schema(description = "휴가 상태", example = "APPROVED")
    private VacationStatus status;

    public static DashboardVacationRequestDTO from(Vacation vacation, Long employeeId) {
        return DashboardVacationRequestDTO.builder()
                .id(vacation.getVacationId())
                .employeeId(employeeId)
                .startDate(vacation.getStartDate())
                .days(vacation.getVacationDays())
                .status(vacation.getVacationStatus())
                .build();
    }
}