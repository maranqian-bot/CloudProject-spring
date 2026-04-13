package kr.co.cloudStudy.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.dto.EmployeeStatsDto;
import kr.co.cloudStudy.employee.entity.Employee;

public interface EmployeeRepositoryCustom {
	//	직원검색 메서드 (이름 Or 직원Id / 부서별 / 상태별)
    Page<Employee> findEmployees(EmployeeSearchDto condition, Pageable pageable);
    // 통계 조회 메서드
    EmployeeStatsDto getEmployeeStats();
}
 