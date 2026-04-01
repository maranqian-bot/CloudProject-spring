package kr.co.cloudStudy.vacation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.vacation.entity.Vacation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "승인 대기 중인 휴가 요청 정보 DTO")
public class PendingVacationApprovalDTO {

    @Schema(description = "휴가 요청 ID", example = "1")
    private Long vacationId;

    @Schema(description = "사원 번호", example = "EMP001")
    private String employeeNumber;

    @Schema(description = "사원명", example = "홍길동")
    private String employeeName;

    @Schema(description = "직급", example = "대리")
    private String position;

    @Schema(description = "휴가 유형", example = "연차")
    private String vacationType;

    @Schema(description = "휴가 시작일", example = "2026-04-01")
    private LocalDate startDate;

    @Schema(description = "휴가 종료일", example = "2026-04-03")
    private LocalDate endDate;

    @Schema(description = "휴가 일수", example = "3")
    private BigDecimal vacationDays;

    @Schema(description = "휴가 사유", example = "개인 일정")
    private String vacationReason;

    @Schema(description = "휴가 상태", example = "PENDING")
    private String vacationStatus;

    public static PendingVacationApprovalDTO from(Vacation vacation) {
        return PendingVacationApprovalDTO.builder()
                .vacationId(vacation.getVacationId())
                .employeeNumber(vacation.getEmployee().getEmployeeNumber())
                .employeeName(vacation.getEmployee().getName())
                .position(vacation.getEmployee().getPosition())
                .vacationType(vacation.getVacationType())
                .startDate(vacation.getStartDate())
                .endDate(vacation.getEndDate())
                .vacationDays(vacation.getVacationDays())
                .vacationReason(vacation.getVacationReason())
                .vacationStatus(vacation.getVacationStatus().name())
                .build();
    }
}