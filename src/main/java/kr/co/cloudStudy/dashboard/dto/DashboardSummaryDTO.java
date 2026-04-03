package kr.co.cloudStudy.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "대시보드 요약 정보 DTO")
public class DashboardSummaryDTO {

    @Schema(description = "오늘 기준 같은 팀 부재 인원 수", example = "2")
    private long absentCount;

    @Schema(description = "현재 로그인 사용자의 이번 달 근무 일수", example = "8")
    private long workDays;

    public static DashboardSummaryDTO of(long absentCount, long workDays) {
        return DashboardSummaryDTO.builder()
                .absentCount(absentCount)
                .workDays(workDays)
                .build();
    }
}