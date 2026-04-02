package kr.co.cloudStudy.vacation.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.vacation.entity.Vacation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "휴가 승인 목록 응답 DTO")
public class VacationRequestListResponseDTO {

    @Schema(description = "요약 정보")
    private VacationRequestListSummaryDTO summary;

    @Schema(description = "목록 데이터")
    private List<VacationRequestListItemDTO> list;

    @Schema(description = "현재 페이지", example = "1")
    private int currentPage;

    @Schema(description = "전체 페이지 수", example = "3")
    private int totalPages;

    @Schema(description = "전체 데이터 수", example = "12")
    private long totalElements;

    @Schema(description = "페이지 크기", example = "5")
    private int pageSize;

    public static VacationRequestListResponseDTO of(
            VacationRequestListSummaryDTO summary,
            Page<Vacation> vacationPage
    ) {
        return VacationRequestListResponseDTO.builder()
                .summary(summary)
                .list(vacationPage.getContent().stream()
                        .map(VacationRequestListItemDTO::from)
                        .toList())
                .currentPage(vacationPage.getNumber() + 1)
                .totalPages(vacationPage.getTotalPages())
                .totalElements(vacationPage.getTotalElements())
                .pageSize(vacationPage.getSize())
                .build();
    }
}