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
            List<VacationRequestListItemDTO> list,
            int currentPage,
            int totalPages,
            long totalElements,
            int pageSize
    ) {
        return VacationRequestListResponseDTO.builder()
                .summary(summary)
                .list(list)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .pageSize(pageSize)
                .build();
    }
}