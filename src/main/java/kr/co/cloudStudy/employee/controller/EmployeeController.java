package kr.co.cloudStudy.employee.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.employee.dto.EmployeeReqDto;
import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@Tag(name = "Employee Controller", description = "직원 관리 API")
public class EmployeeController {
	
	private final EmployeeService employeeService;	// 서비스 호출용
	
	@Operation(summary = "직원 목록 조회", description = "검색 조건과 페이징 처리를 적용하여 직원 목록을 조회합니다.")
	@GetMapping
	public Page<EmployeeResDto> getEmployeeList(	// 검색조건과, 페이징처리 해서 응답dto 반환해주는 메서드
	        EmployeeSearchDto condition, 																				// 검색조건과,
	        @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {		// 페이징처리

	    
	    return employeeService.getEmployeeList(condition, pageable);	
	}
	@Operation(summary = "새로운 직원 추가", description = "직원 추가후 응답을 반환합니다.")
	@PostMapping("/post")
	public ResponseEntity<EmployeeResDto> postEmployee(@RequestBody EmployeeReqDto employeeReqDto) {	

		
		
		EmployeeResDto response = employeeService.saveEmployee(employeeReqDto);	// 저장후에, 응답Dto가 반환값으로 옴.
		return ResponseEntity.status(HttpStatus.CREATED).body(response);	 
	}
	
	// 직원 수정 처리
	
	@Operation(summary = "직원정보 수정", description = "직원정보 수정 후 응답을 반환합니다.")
	@PutMapping("/edit/{id}")
	public ResponseEntity<EmployeeResDto> editEmployee(@PathVariable Long id, 
														@RequestBody EmployeeReqDto reqDto) {
		EmployeeResDto updateEmployee = employeeService.editEmployee(id, reqDto);	// 갱신된 값
		return ResponseEntity.ok(updateEmployee);		// 갱신된거 상태 담아서 전달하기..
		
	}
	
}
 




