package kr.co.cloudStudy.employee.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;

public interface EmployeeRepositoryCustom {
    Page<EmployeeEntity> searchEmployees(EmployeeSearchDto condition, Pageable pageable);
}
