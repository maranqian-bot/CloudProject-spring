package kr.co.cloudStudy.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.cloudStudy.department.entity.Department;
import kr.co.cloudStudy.employee.dto.EmployeeReqDto;
import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;

public interface EmployeeService {
	
	// 직원 추가를 위한 레포지토리 불러오기.
	
	// 페이징 처리된 응답 dto를 반환... impl에서 검색 기능 구현하기
	Page<EmployeeResDto> getEmployeeList(EmployeeSearchDto condition, Pageable pageable);
	
	// 직원추가 하기
	//	- 매개변수로 저장요청 객체가 들어옴 -> saveEmployee 실행하여 데이터 저장후 -> 응답객체 반환( 화면에 띄울거 )
	EmployeeResDto saveEmployee(EmployeeReqDto employeeReqDto);
	
	// 직원 수정.
	//	-  반환으로는 응답dto	
//					: 매개변수로 직원아이디, 부서 아이디와, 요청객체를 받아서 처리함.
	public EmployeeResDto editEmployee( Long employeeId,Long departmentId, EmployeeReqDto employeeResDto);
} 
 