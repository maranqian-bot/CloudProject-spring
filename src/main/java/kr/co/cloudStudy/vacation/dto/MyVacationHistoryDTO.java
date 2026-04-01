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
public class MyVacationHistoryDTO {

    private Long vacationId;
    private String vacationType;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal vacationDays;
    private String vacationStatus;
}