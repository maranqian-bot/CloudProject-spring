//	검색기능 보류함 
package kr.co.cloudStudy.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.cloudStudy.employee.entity.Employee;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee,Long>, EmployeeRepositoryCustom{

	Optional<Employee> findByEmployeeNumber(String employeeNumber);
	
	Optional<Employee> findByEmail(String email);
	
}	





