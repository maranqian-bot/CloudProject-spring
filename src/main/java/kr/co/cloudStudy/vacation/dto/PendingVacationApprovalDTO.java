package kr.co.cloudStudy.vacation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingVacationApprovalDTO {

    private Long vacationId;
    private String employeeNumber;
    private String employeeName;
    private String position;
    private String vacationType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal vacationDays;
    private String vacationReason;
    private String vacationStatus;
}