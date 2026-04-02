package kr.co.cloudStudy.vacation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "휴가 반려 요청 DTO")
public class VacationRejectRequestDTO {

    @Schema(description = "반려 사유", example = "일정 조정이 필요합니다.")
    private String rejectReason;
}