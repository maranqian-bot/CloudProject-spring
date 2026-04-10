package kr.co.cloudStudy.vacation.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import kr.co.cloudStudy.vacation.controller.docs.VacationRequestListControllerDocs;
import kr.co.cloudStudy.vacation.dto.VacationDecisionResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRejectRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListSummaryDTO;
import kr.co.cloudStudy.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vacation-request-list")
@RequiredArgsConstructor
@Tag(name = "Vacation Request List", description = "휴가 승인 목록 API")
public class VacationRequestListController implements VacationRequestListControllerDocs {

    private final VacationService vacationService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponseDTO<VacationRequestListResponseDTO>> getVacationRequestList(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "type", defaultValue = "ALL") String type,
            @Parameter(hidden = true) Principal principal
    ) {
        VacationRequestListResponseDTO response =
                vacationService.getVacationRequestList(principal.getName(), page, size, type);

        return ResponseEntity.ok(
                ApiResponseDTO.success("휴가 승인 목록 조회 성공", response)
        );
    }

    @Override
    @GetMapping("/summary")
    public ResponseEntity<ApiResponseDTO<VacationRequestListSummaryDTO>> getVacationRequestListSummary(
            @Parameter(hidden = true) Principal principal
    ) {
        VacationRequestListSummaryDTO response =
                vacationService.getVacationRequestListSummary(principal.getName());

        return ResponseEntity.ok(
                ApiResponseDTO.success("휴가 승인 목록 요약 조회 성공", response)
        );
    }

    @Override
    @PatchMapping("/{vacationId}/approve")
    public ResponseEntity<ApiResponseDTO<VacationDecisionResponseDTO>> approveVacationRequest(
            @PathVariable("vacationId") Long vacationId,
            @Parameter(hidden = true) Principal principal
    ) {
        VacationDecisionResponseDTO response =
                vacationService.approveVacationRequest(vacationId, principal.getName());

        return ResponseEntity.ok(
                ApiResponseDTO.success("휴가 승인 처리 성공", response)
        );
    }

    @Override
    @PatchMapping("/{vacationId}/reject")
    public ResponseEntity<ApiResponseDTO<VacationDecisionResponseDTO>> rejectVacationRequest(
            @PathVariable("vacationId") Long vacationId,
            @RequestBody VacationRejectRequestDTO request,
            @Parameter(hidden = true) Principal principal
    ) {
        VacationDecisionResponseDTO response =
                vacationService.rejectVacationRequest(vacationId, request, principal.getName());

        return ResponseEntity.ok(
                ApiResponseDTO.success("휴가 반려 처리 성공", response)
        );
    }
}