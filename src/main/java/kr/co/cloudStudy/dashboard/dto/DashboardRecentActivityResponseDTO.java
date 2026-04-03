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
@Schema(description = "대시보드 최근 활동 목록 응답 DTO")
public class DashboardRecentActivityResponseDTO {

    @Schema(description = "최근 활동 목록")
    private List<DashboardRecentActivityItemDTO> items;

    @Schema(description = "전체 데이터 수", example = "21")
    private long totalCount;

    @Schema(description = "현재 페이지", example = "1")
    private int currentPage;

    @Schema(description = "전체 페이지 수", example = "5")
    private int totalPages;

    @Schema(description = "페이지 크기", example = "5")
    private int pageSize;

    public static DashboardRecentActivityResponseDTO of(
            List<DashboardRecentActivityItemDTO> items,
            long totalCount,
            int currentPage,
            int totalPages,
            int pageSize
    ) {
        return DashboardRecentActivityResponseDTO.builder()
                .items(items)
                .totalCount(totalCount)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .pageSize(pageSize)
                .build();
    }
}