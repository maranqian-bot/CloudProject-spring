package kr.co.cloudStudy.vacation.controller.docs;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestEmployeeResponseDTO;

public interface VacationRequestControllerDocs {

    @Operation(
            summary = "휴가 신청 페이지 대상자 정보 조회",
            description = "휴가 신청 페이지에서 사용할 직원 기본 정보와 사용 가능 연차를 조회합니다."
    )
    ResponseEntity<ApiResponseDTO<VacationRequestEmployeeResponseDTO>> getVacationRequestEmployee(
            @Parameter(description = "직원 사번", example = "EMP-2024-002")
            String employeeNumber,

            @Parameter(description = "조회 연도", example = "2026")
            Integer year
    );

    @Operation(
            summary = "휴가 신청 등록",
            description = "휴가 신청 정보를 등록합니다."
    )
    ResponseEntity<ApiResponseDTO<VacationCreateResponseDTO>> createVacationRequest(
            VacationCreateRequestDTO request
    );
}