package kr.co.cloudStudy.department.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.co.cloudStudy.department.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

	// 모든 부서 목록을 페이지 단위로 조회
	Page<Department> findAll(Pageable pageable);
	
	// 부서들을 페이지 단위로 조회(검색용)
	Page<Department> findByDeptNameContaining(String deptName, Pageable pageable);
	
	// 해당 관리자가 맡은 부서들 조회 
	Page<Department> findByManagerId(Long managerId, Pageable pageable);
	
}
