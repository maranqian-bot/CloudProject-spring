package kr.co.cloudStudy.vacation.controller.docs;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementResponseDTO;

public interface VacationManagementControllerDocs {

    @Operation(
            summary = "휴가 관리 페이지 전체 데이터 조회",
            description = "특정 직원의 휴가 관리 페이지에 필요한 상단 요약, 내 휴가 이력, 승인 대기 목록을 조회합니다."
    )
    ResponseEntity<ApiResponseDTO<VacationManagementResponseDTO>> getVacationManagementPage(
            @Parameter(description = "직원 사번", example = "EMP-2024-002")
            String employeeNumber,

            @Parameter(description = "승인자 직원 ID", example = "1")
            Long approverId,

            @Parameter(description = "조회 연도", example = "2026")
            Integer year
    );
}