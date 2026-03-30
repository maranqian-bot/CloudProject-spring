package kr.co.cloudStudy.employee.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.service.EmployeeServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:3000")
public class EmployeeController {
	private final EmployeeServiceImpl employeeServiceImpl;
	@GetMapping
	public Page<EmployeeResDto> getEmployeeList(	// 검색조건과, 페이징처리 해서 응답dto 반환해주는 메서드
	        EmployeeSearchDto condition, 																				// 검색조건과,
	        @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {		// 페이징처리

	    
	    return employeeServiceImpl.getEmployeeList(condition, pageable);	
	}
}
