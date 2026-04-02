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
@Schema(description = "휴가 신청 생성 응답 DTO")
public class VacationCreateResponseDTO {

    @Schema(description = "휴가 신청 ID", example = "15")
    private Long id;

    @Schema(description = "직원 사번", example = "EMP-2024-002")
    private String employeeNumber;

    @Schema(description = "직원 이름", example = "홍길동")
    private String employeeName;

    @Schema(description = "휴가 유형", example = "ETC")
    private VacationType vacationType;

    @Schema(description = "휴가 시작일", example = "2026-04-10")
    private LocalDate startDate;

    @Schema(description = "휴가 종료일", example = "2026-04-10")
    private LocalDate endDate;

    @Schema(description = "휴가 일수", example = "1")
    private BigDecimal days;

    @Schema(description = "휴가 상태", example = "PENDING")
    private VacationStatus status;

    @Schema(description = "휴가 사유", example = "가족 행사 참석")
    private String reason;

    public static VacationCreateResponseDTO from(Vacation vacation) {
        return VacationCreateResponseDTO.builder()
                .id(vacation.getVacationId())
                .employeeNumber(vacation.getEmployee().getEmployeeNumber())
                .employeeName(vacation.getEmployee().getName())
                .vacationType(vacation.getVacationType())
                .startDate(vacation.getStartDate())
                .endDate(vacation.getEndDate())
                .days(vacation.getVacationDays())
                .status(vacation.getVacationStatus())
                .reason(vacation.getVacationReason())
                .build();
    }
}