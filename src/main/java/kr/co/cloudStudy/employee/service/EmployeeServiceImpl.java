package kr.co.cloudStudy.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor


public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository employeeRepository;	// 인터페이스 받아오는 곳
	
	@Override
	public Page<EmployeeResDto> getEmployeeList(EmployeeSearchDto condition, Pageable pageable) {	// 검색조건과, 페이지 객체를 받고
		return employeeRepository.searchEmployees(condition, pageable)	// 매개변수로 받은거 적용하여서 데이터 검색해줌
				.map(EmployeeResDto :: fromEntity);		// 검색해온 데이터에 대해 일단 dto변환 함수만 가져온다. 그리고 대기.
	};
	
}
