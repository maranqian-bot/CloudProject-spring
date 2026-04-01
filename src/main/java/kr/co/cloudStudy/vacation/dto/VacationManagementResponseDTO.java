package kr.co.cloudStudy.vacation.dto;

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
@Schema(description = "휴가 관리 페이지 응답 DTO")
public class VacationManagementResponseDTO {

    @Schema(description = "휴가 관리 요약 정보")
    private VacationManagementSummaryDTO summary;

    @Schema(description = "내 휴가 이력 목록")
    private List<MyVacationHistoryDTO> myVacationHistories;

    @Schema(description = "승인 대기 중인 휴가 요청 목록")
    private List<PendingVacationApprovalDTO> pendingApprovals;
}