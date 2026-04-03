package kr.co.cloudStudy.dashboard.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "대시보드 메인 응답 DTO")
public class DashboardResponseDTO {

    @Schema(description = "대시보드 요약 정보")
    private DashboardSummaryDTO summary;

    @Schema(description = "현재 로그인 사용자 정보")
    private DashboardCurrentEmployeeDTO currentEmployee;

    @Schema(description = "대시보드 표시용 휴가 요청 목록")
    private List<DashboardVacationRequestDTO> vacationRequests;

    public static DashboardResponseDTO of(
            DashboardSummaryDTO summary,
            DashboardCurrentEmployeeDTO currentEmployee,
            List<DashboardVacationRequestDTO> vacationRequests
    ) {
        return DashboardResponseDTO.builder()
                .summary(summary)
                .currentEmployee(currentEmployee)
                .vacationRequests(vacationRequests)
                .build();
    }
}