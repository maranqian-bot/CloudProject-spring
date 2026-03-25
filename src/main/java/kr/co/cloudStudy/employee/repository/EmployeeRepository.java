


package kr.co.cloudStudy.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import lombok.RequiredArgsConstructor;

@Repository

public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>, EmployeeRepositoryCustom{
	

}	