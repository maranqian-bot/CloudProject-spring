package kr.co.cloudStudy.vacation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import kr.co.cloudStudy.vacation.controller.docs.VacationManagementControllerDocs;
import kr.co.cloudStudy.vacation.dto.VacationManagementResponseDTO;
import kr.co.cloudStudy.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vacation-management")
@RequiredArgsConstructor
@Tag(name = "Vacation Management", description = "휴가 관리 조회 API")
public class VacationManagementController implements VacationManagementControllerDocs {

    private final VacationService vacationService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponseDTO<VacationManagementResponseDTO>> getVacationManagementPage(
            @RequestParam("employeeNumber") String employeeNumber,
            @RequestParam("approverEmployeeNumber") String approverEmployeeNumber,
            @RequestParam("year") Integer year
    ) {
        VacationManagementResponseDTO response =
                vacationService.getVacationManagementPage(employeeNumber, approverEmployeeNumber, year);

        return ResponseEntity.ok(
                ApiResponseDTO.success("휴가 관리 페이지 조회 성공", response)
        );
    }
}