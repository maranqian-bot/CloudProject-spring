package kr.co.cloudStudy.attendance.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "근태 이력 페이징 응답 DTO")
public class AttendanceHistoryPageResponseDTO {
	
    @Schema(description = "근태 이력 목록")
    private List<AttendanceHistoryResponseDTO> content;

    @Schema(description = "현재 페이지 (0부터 시작)", example = "0")
    private int currentPage;

    @Schema(description = "페이지 크기", example = "10")
    private int size;

    @Schema(description = "전체 페이지 수", example = "5")
    private int totalPages;

    @Schema(description = "전체 데이터 개수", example = "42")
    private long totalElements;

    @Schema(description = "첫 페이지 여부", example = "true")
    private boolean first;

    @Schema(description = "마지막 페이지 여부", example = "false")
    private boolean last;
}
