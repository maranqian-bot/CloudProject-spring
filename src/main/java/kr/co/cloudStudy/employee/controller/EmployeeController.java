package kr.co.cloudStudy.employee.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
@Tag(name = "Employee", description = "직원 관리 API")

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor

public class EmployeeController {
    
    private final EmployeeService employeeService;
    @Operation(summary = "직원 목록 조회", description = "검색 조건(condition)과 페이징 정보를 이용해 직원 목록을 조회합니다.")
    @GetMapping
    public Page<EmployeeResDto> getEmployeeList(
            EmployeeSearchDto condition, // 
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        
        return employeeService.searchEmployees(condition, pageable);
    }
}