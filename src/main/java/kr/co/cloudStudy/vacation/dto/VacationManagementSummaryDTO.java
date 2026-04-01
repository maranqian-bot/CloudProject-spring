package kr.co.cloudStudy.vacation.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacationManagementSummaryDTO {

    private BigDecimal availableVacationDays;
    private BigDecimal usedVacationDays;
    private long pendingApprovalCount;
}