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

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "직원 관리 API")
public class EmployeeController {
	private final EmployeeService employeeService;
	@Operation(summary = "직원 목록 조회", description = "검색 조건과 페이징 처리를 적용하여 직원 목록을 조회합니다.")
	@GetMapping
	public Page<EmployeeResDto> getEmployeeList(	// 검색조건과, 페이징처리 해서 응답dto 반환해주는 메서드
	        EmployeeSearchDto condition, 																				// 검색조건과,
	        @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {		// 페이징처리

	    
	    return employeeService.getEmployeeList(condition, pageable);	
	}
}
