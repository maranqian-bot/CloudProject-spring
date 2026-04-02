package kr.co.cloudStudy.vacation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.vacation.entity.VacationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "휴가 신청 생성 요청 DTO")
public class VacationCreateRequestDTO {

    @Schema(description = "직원 사번", example = "EMP-2024-002")
    private String employeeNumber;

    @Schema(description = "대리 신청자 사번", example = "EMP-2024-010")
    private String proxyEmployeeNumber;

    @Schema(description = "휴가 유형", example = "ETC")
    private VacationType vacationType;

    @Schema(description = "기타 상세 사유", example = "가족 행사 참석")
    private String reasonDetail;

    @Schema(description = "휴가 시작일", example = "2026-04-10")
    private LocalDate startDate;

    @Schema(description = "사용 일수", example = "1")
    private BigDecimal days;
}