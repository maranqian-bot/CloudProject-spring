package kr.co.cloudStudy.vacation.controller.docs;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationDecisionResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRejectRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListSummaryDTO;

public interface VacationRequestListControllerDocs {

    @Operation(
            summary = "휴가 승인 목록 조회",
            description = "로그인한 승인자 기준으로 휴가 승인 페이지에서 사용할 목록 데이터와 페이징 정보를 조회합니다."
    )
    ResponseEntity<ApiResponseDTO<VacationRequestListResponseDTO>> getVacationRequestList(
            @Parameter(description = "페이지 번호(1부터 시작)", example = "1")
            Integer page,

            @Parameter(description = "페이지 크기", example = "5")
            Integer size,

            @Parameter(description = "휴가 유형 필터", example = "ALL")
            String type,

            @Parameter(hidden = true)
            Principal principal
    );

    @Operation(
            summary = "휴가 승인 목록 요약 조회",
            description = "로그인한 승인자 기준으로 휴가 승인 페이지 상단 요약 정보를 조회합니다."
    )
    ResponseEntity<ApiResponseDTO<VacationRequestListSummaryDTO>> getVacationRequestListSummary(
            @Parameter(hidden = true)
            Principal principal
    );

    @Operation(
            summary = "휴가 승인 처리",
            description = "로그인한 승인자가 대기 중인 휴가 신청을 승인 처리합니다."
    )
    ResponseEntity<ApiResponseDTO<VacationDecisionResponseDTO>> approveVacationRequest(
            @Parameter(description = "휴가 신청 ID", example = "12")
            Long vacationId,

            @Parameter(hidden = true)
            Principal principal
    );

    @Operation(
            summary = "휴가 반려 처리",
            description = "로그인한 승인자가 대기 중인 휴가 신청을 반려 처리합니다."
    )
    ResponseEntity<ApiResponseDTO<VacationDecisionResponseDTO>> rejectVacationRequest(
            @Parameter(description = "휴가 신청 ID", example = "12")
            Long vacationId,

            VacationRejectRequestDTO request,

            @Parameter(hidden = true)
            Principal principal
    );
}