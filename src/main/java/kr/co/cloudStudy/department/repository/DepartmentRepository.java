package kr.co.cloudStudy.department.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kr.co.cloudStudy.department.entity.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	// 부서 코드로 조회
	Optional<Department> findByDeptCode(String deptCode);
	
	// 부서 이름으로 조회
	Optional<Department> findByDeptName(String deptName);
	
	// 해당 관리자가 맡은 부서들 조회  (해당 관리자가 여러 부서들을 맡을 수 있으므로 'List'로 작성함)
	List<Department> findByManagerId(Long managerId);
	
}
