//	검색기능 보류함 


package kr.co.cloudStudy.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.cloudStudy.employee.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity,Long>, EmployeeRepositoryCustom{
	// 필터링 및 검색기능 (보류)
	/*
	 * 
	@Query("Select e FROM EmployeeEntity e"
			
			)
	Page<EmployeeEntity> findEmployeeByFilters();
 
	 * */
	
	//부서 목록 조회 (보류)
	

}	





