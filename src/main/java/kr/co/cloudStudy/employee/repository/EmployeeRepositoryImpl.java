
package kr.co.cloudStudy.employee.repository;

import static kr.co.cloudStudy.employee.entity.QEmployee.employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.dto.EmployeeStatsDto;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Employee> searchEmployees(EmployeeSearchDto condition, Pageable pageable) {
        // 1. 기본 쿼리 생성
        StringBuilder jpql = new StringBuilder("SELECT e FROM Employee e WHERE 1=1");
        
        // 2. 동적 조건 추가 (StringUtils로 공백/null 체크)
        if (StringUtils.hasText(condition.getKeyword())) {
            // 이름(name) 필드에 키워드가 포함되는지 검색
            jpql.append(" AND e.name LIKE :keyword");
        }
        
        if (StringUtils.hasText(condition.getDepartment())) {
            // 부서(deptName) 필드 매칭
            jpql.append(" AND e.department.deptName = :department");
        }

        if (condition.getDepartmentId() != null) {
            // 부서(departmentId) 필드 매칭 (엔티티 필드명에 맞춰 로직 추가)
            jpql.append(" AND e.department.departmentId = :departmentId");
        }
        
        if (StringUtils.hasText(condition.getStatus())) {
            // 상태값(status) 비교
            jpql.append(" AND e.status = :status");
        }

        // 3. 메인 데이터 조회용 쿼리 생성
        TypedQuery<Employee> query = em.createQuery(jpql.toString(), Employee.class);
        
        // 4. 파라미터 바인딩
        setParameters(query, condition);
        
        // 5. 페이징 적용 (0부터 시작하는 offset 계산)
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Employee> resultList = query.getResultList();
        
        // 6. 전체 개수 조회용 카운트 쿼리 (페이징 처리에 필요)
        String countJpql = "SELECT COUNT(e) FROM Employee e WHERE 1=1";
        if (StringUtils.hasText(condition.getKeyword())) countJpql += " AND e.name LIKE :keyword";
        if (StringUtils.hasText(condition.getDepartment())) countJpql += " AND e.department.deptName = :department";
        if (condition.getDepartmentId() != null) countJpql += " AND e.department.departmentId = :departmentId";
        if (StringUtils.hasText(condition.getStatus())) countJpql += " AND e.status = :status";
        
        TypedQuery<Long> countQuery = em.createQuery(countJpql, Long.class);
        setParameters(countQuery, condition);
        
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(resultList, pageable, total != null ? total : 0L);
    }

    // 파라미터 바인딩 공통 로직
    private void setParameters(TypedQuery<?> query, EmployeeSearchDto condition) {
        if (StringUtils.hasText(condition.getKeyword())) {
            query.setParameter("keyword", "%" + condition.getKeyword() + "%");
        }
        if (StringUtils.hasText(condition.getDepartment())) {
            query.setParameter("department", condition.getDepartment());
        }
        if (StringUtils.hasText(condition.getStatus())) {
            // DB 필드 타입이 Integer라면 Integer.parseInt() 사용
            query.setParameter("status", condition.getStatus());
        }
        if (condition.getDepartmentId() != null) {
            query.setParameter("departmentId", condition.getDepartmentId());
        }
    }

	@Override
	// 고용형태 객체 필드에 :
	//	- querydsl로 조회한 것을 고용형태 객체 빌더에 담아서 값을 전달
	public EmployeeStatsDto getEmployeeStats() {
		// 총 정규직 카운트
		//	- null 일 수도 있음 , OrElse로 0전달
		//	- querydsl의 count기능 사용.
		// 	- 고용형태가 정규직이고, 상태가 활성인것.
		Long regularCountResult = Optional.ofNullable(
				queryFactory
					.select(employee.count())		//	조회할것(카운트).
					.from(employee)
					.where(employee.employeeType.eq("Regular"), employee.status.eq("ACTIVE"))
					.fetchOne()		// 실행버튼(단건)
					).orElse(0L);
		String regularCountStr = String.format("%,d", regularCountResult);
		// 계약직 카운트
		//	-	null일 수 있음
		//	-	count 사용 
		//	-	고용형태가 계약직이고, 상태가 활성인 것.
		Long contractResult = Optional.ofNullable(
				queryFactory
					.select(employee.count())
					.from(employee)
					.where(employee.employeeType.eq("Contract"), employee.status.eq("ACTIVE"))
					.fetchOne()
		).orElse(0L);
		String contractCountStr	= String.format("%,d", contractResult);
		// 평균 근속 연수
		// -	입사일이 존재하고, 활성상태인 직원중에서 계산.
		Double avgResult = Optional.ofNullable(
				queryFactory
				.select(employee.hireDate.year().longValue().subtract(LocalDate.now().getYear()).abs().avg())
				.from(employee)
				.where(employee.status.eq("ACTIVE"),employee.hireDate.isNotNull())
				.fetchOne()
				).orElse(0.0);
				String avgWorkingYearStr = String.format("%.1f년",avgResult );
		
		return EmployeeStatsDto.builder()
					.regularCount(regularCountStr)
					.contractCount(contractCountStr)
					.avgWorkingYears(avgWorkingYearStr)
					.growthRate("+12.4%")
					.build();
	}

	
    	
}