package kr.co.cloudStudy.vacation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "휴가 승인/반려 처리 응답 DTO")
public class VacationDecisionResponseDTO {

    @Schema(description = "휴가 신청 ID", example = "12")
    private Long id;

    @Schema(description = "처리 상태", example = "APPROVED")
    private VacationStatus status;

    @Schema(description = "승인자 이름", example = "김관리")
    private String approverName;

    @Schema(description = "반려 사유", example = "일정 조정이 필요합니다.")
    private String rejectReason;

    public static VacationDecisionResponseDTO from(Vacation vacation) {
        return VacationDecisionResponseDTO.builder()
                .id(vacation.getVacationId())
                .status(vacation.getVacationStatus())
                .approverName(vacation.getApproverName())
                .rejectReason(vacation.getRejectReason())
                .build();
    }
}