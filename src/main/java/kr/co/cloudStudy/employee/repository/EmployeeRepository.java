//	검색기능 보류함 


package kr.co.cloudStudy.employee.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.cloudStudy.employee.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>{
	
	Optional<EmployeeEntity> findByEmployeeNumber(String employeeNumber);
	
}	





