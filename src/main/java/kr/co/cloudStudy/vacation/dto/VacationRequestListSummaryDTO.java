package kr.co.cloudStudy.vacation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "휴가 승인 목록 요약 DTO")
public class VacationRequestListSummaryDTO {

    @Schema(description = "대기 건수", example = "2")
    private long pendingCount;

    @Schema(description = "승인 건수", example = "5")
    private long approvedCount;

    @Schema(description = "반려 건수", example = "1")
    private long rejectedCount;

    @Schema(description = "이번 달 휴가 건수", example = "3")
    private long monthlyVacationCount;

    public static VacationRequestListSummaryDTO of(
            long pendingCount,
            long approvedCount,
            long rejectedCount,
            long monthlyVacationCount
    ) {
        return VacationRequestListSummaryDTO.builder()
                .pendingCount(pendingCount)
                .approvedCount(approvedCount)
                .rejectedCount(rejectedCount)
                .monthlyVacationCount(monthlyVacationCount)
                .build();
    }
}