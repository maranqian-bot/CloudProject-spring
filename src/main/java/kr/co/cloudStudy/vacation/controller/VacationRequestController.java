package kr.co.cloudStudy.vacation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import kr.co.cloudStudy.vacation.controller.docs.VacationRequestControllerDocs;
import kr.co.cloudStudy.vacation.dto.VacationCreateRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestEmployeeResponseDTO;
import kr.co.cloudStudy.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vacation-request")
@RequiredArgsConstructor
@Tag(name = "Vacation Request", description = "휴가 신청 API")
public class VacationRequestController implements VacationRequestControllerDocs {

    private final VacationService vacationService;

    @Override
    @GetMapping("/employees/{employeeNumber}")
    public ResponseEntity<ApiResponseDTO<VacationRequestEmployeeResponseDTO>> getVacationRequestEmployee(
            @PathVariable String employeeNumber,
            @RequestParam Integer year
    ) {
        VacationRequestEmployeeResponseDTO response =
                vacationService.getVacationRequestEmployee(employeeNumber, year);

        return ResponseEntity.ok(
                ApiResponseDTO.success("휴가 신청 대상자 정보 조회 성공", response)
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<ApiResponseDTO<VacationCreateResponseDTO>> createVacationRequest(
            @RequestBody VacationCreateRequestDTO request
    ) {
        VacationCreateResponseDTO response = vacationService.createVacationRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(HttpStatus.CREATED, "휴가 신청 등록 성공", response));
    }
}