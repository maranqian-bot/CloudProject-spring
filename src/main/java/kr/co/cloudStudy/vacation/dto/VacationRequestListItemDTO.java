package kr.co.cloudStudy.vacation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;
import kr.co.cloudStudy.vacation.entity.VacationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "휴가 승인 목록 항목 DTO")
public class VacationRequestListItemDTO {

    @Schema(description = "휴가 신청 ID", example = "12")
    private Long id;

    @Schema(description = "사원명", example = "홍길동")
    private String employeeName;

    @Schema(description = "부서명", example = "인사부")
    private String departmentName;

    @Schema(description = "직급", example = "대리")
    private String position;

    @Schema(description = "휴가 유형", example = "ANNUAL")
    private VacationType vacationType;

    @Schema(description = "휴가 시작일", example = "2026-04-10")
    private LocalDate startDate;

    @Schema(description = "휴가 종료일", example = "2026-04-10")
    private LocalDate endDate;

    @Schema(description = "휴가 일수", example = "1")
    private BigDecimal days;

    @Schema(description = "휴가 상태", example = "PENDING")
    private VacationStatus status;

    public static VacationRequestListItemDTO from(Vacation vacation) {
        return VacationRequestListItemDTO.builder()
                .id(vacation.getVacationId())
                .employeeName(vacation.getEmployee().getName())
                .departmentName(vacation.getEmployee().getDepartment().getDeptName())
                .position(vacation.getEmployee().getPosition())
                .vacationType(vacation.getVacationType())
                .startDate(vacation.getStartDate())
                .endDate(vacation.getEndDate())
                .days(vacation.getVacationDays())
                .status(vacation.getVacationStatus())
                .build();
    }
}