package kr.co.cloudStudy.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;


public interface EmployeeService {
	// 페이징 처리된 응답 dto를 반환... impl에서 검색 기능 구현하기
	Page<EmployeeResDto> getEmployeeList(EmployeeSearchDto condition, Pageable pageable);
}
