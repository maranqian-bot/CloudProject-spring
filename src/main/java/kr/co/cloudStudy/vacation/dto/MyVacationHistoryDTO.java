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
@Schema(description = "내 휴가 이력 정보 DTO")
public class MyVacationHistoryDTO {

    @Schema(description = "휴가 요청 ID", example = "1")
    private Long vacationId;

    @Schema(description = "휴가 유형", example = "ANNUAL")
    private VacationType vacationType;

    @Schema(description = "휴가 시작일", example = "2026-03-20")
    private LocalDate startDate;

    @Schema(description = "휴가 종료일", example = "2026-03-21")
    private LocalDate endDate;

    @Schema(description = "휴가 일수", example = "2")
    private BigDecimal vacationDays;

    @Schema(description = "휴가 상태", example = "APPROVED")
    private VacationStatus vacationStatus;

    public static MyVacationHistoryDTO from(Vacation vacation) {
        return MyVacationHistoryDTO.builder()
                .vacationId(vacation.getVacationId())
                .vacationType(vacation.getVacationType())
                .startDate(vacation.getStartDate())
                .endDate(vacation.getEndDate())
                .vacationDays(vacation.getVacationDays())
                .vacationStatus(vacation.getVacationStatus())
                .build();
    }
}