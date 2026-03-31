package kr.co.cloudStudy.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.entity.Employee;

public interface EmployeeRepositoryCustom {
    Page<Employee> searchEmployees(EmployeeSearchDto condition, Pageable pageable);
}
