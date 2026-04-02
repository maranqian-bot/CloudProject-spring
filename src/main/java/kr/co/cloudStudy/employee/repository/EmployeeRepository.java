//	검색기능 보류함 
package kr.co.cloudStudy.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.cloudStudy.employee.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {
	Optional<Employee> findByEmployeeNumber(String employeeNumber);
	
	Optional<Employee> findByEmail(String email);
	
  // 휴가 신청 페이지에서는 department 즉시 조회가 필요함
  @EntityGraph(attributePaths = "department")
  Optional<Employee> findWithDepartmentByEmployeeNumber(String employeeNumber);
  
}