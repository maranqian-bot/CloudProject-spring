package kr.co.cloudStudy.vacation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacationManagementResponseDTO {

    private VacationManagementSummaryDTO summary;
    private List<MyVacationHistoryDTO> myVacationHistories;
    private List<PendingVacationApprovalDTO> pendingApprovals;
}