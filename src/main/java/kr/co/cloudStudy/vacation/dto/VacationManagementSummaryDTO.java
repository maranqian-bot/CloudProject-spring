package kr.co.cloudStudy.vacation.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "휴가 관리 요약 정보 DTO")
public class VacationManagementSummaryDTO {

    @Schema(description = "잔여 휴가 일수", example = "12")
    private BigDecimal availableVacationDays;

    @Schema(description = "사용한 휴가 일수", example = "3")
    private BigDecimal usedVacationDays;

    @Schema(description = "승인 대기 건수", example = "2")
    private long pendingApprovalCount;

    public static VacationManagementSummaryDTO of(
            BigDecimal availableVacationDays,
            BigDecimal usedVacationDays,
            long pendingApprovalCount
    ) {
        return VacationManagementSummaryDTO.builder()
                .availableVacationDays(availableVacationDays)
                .usedVacationDays(usedVacationDays)
                .pendingApprovalCount(pendingApprovalCount)
                .build();
    }
}