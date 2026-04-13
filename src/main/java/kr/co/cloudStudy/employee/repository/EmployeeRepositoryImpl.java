
package kr.co.cloudStudy.employee.repository;

import static kr.co.cloudStudy.department.entity.QDepartment.department;
import static kr.co.cloudStudy.employee.entity.QEmployee.employee;
import static org.springframework.util.StringUtils.hasText;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.dto.EmployeeStatsDto;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {


	// querydsl 사용
	private final JPAQueryFactory queryFactory;


	// 직원 검색 메서드 분기 담당 ( keywordContains, deptNameEq, employeeStatusEq, deparmentIdEq )

	// 1. 이름 or 직원사번( 예시: #78244)
	// - 입력 (포함)하거나, 해당 조건 입력하지 않았을 때.
	private BooleanExpression keywordContains(String keyword) {
		// 입력값이 null, "", " " 이 아닐지 부터 검사.
		if (!hasText(keyword)) {
			return null;
		} // 위의 hasText검사 통과...

		// keyword로 이름 검색조건 만들기( 일치 또는 포함 ).
		BooleanExpression nameOrNumber = employee.name.contains(keyword);
		// keyword가 만약 #7228..의 형태이면 직원사번까지 조건 검색 만들음. ( 일치 또는 포함 )
		if (keyword.contains("#") || keyword.matches("#\\d+")) {
			nameOrNumber = nameOrNumber.or(employee.employeeNumber.contains(keyword));
		}
		return nameOrNumber;
	}
 
		// 2. 부서명을 선택하거나, 해당조건 선택 안한경우
		private BooleanExpression deptNameEq(String deptName) {
			// 입력값이 null, "", " " 이거나, "모든부서" 라면
			// 부서 필터링 조건 없음
			if (!hasText(deptName) || deptName.equals("모든 부서")) {
				return null;
			}
			// 그 외의 경우에만 부서 필터링 조건 생성
			return department.deptName.eq(deptName);
		}


	// 3. 상태를 선택하거나, 해당 조건 선택 안한경우
	private BooleanExpression employeeStatusEq(String status) {
		// 입력값이 null, "", " " 이거나, "상태: 전체" 라면
		// 상태 필터링 조건 없음.
		if (!hasText(status) || status.equals("상태: 전체")) {
			return null;
		}
		// 그 외의 경우에만 상태 필터링 조건 만들기
		return employee.status.eq(status);
	}

	// 4. 부서 Id 선택하거나, 해당 조건 선택 안했을 떄
	private BooleanExpression departmentIdEq(Long departmentId) {
		// null인 경우엔 부서id where조건은 null
		if( departmentId == null) {
			return null;
		}
		// null이 아닐 때, where = department_id = 매개변수
		return employee.department.departmentId.eq(departmentId);
		
	}
	
	@Override
	// 직원 검색 메서드		: 검색 조건과, 페이징 방식을 전달
	// - (위에서 만들어 둔 keywordContains, deptNameEq, employeeStatusEq 사용..)
	// - where 조건을 주어서 실제로 검색하는 부분..
	// - 이름 or 직원id / 부서별 / 상태별
	// - 위의 세가지 중 선택하여 검색...
	public Page<Employee> findEmployees(EmployeeSearchDto condition, Pageable pageable) {
		// 검색 결과
		List<Employee> content = queryFactory
				.select(employee)
				.from(employee)
				.leftJoin(employee.department, department).fetchJoin()
				.where(
						keywordContains(condition.getKeyword()),
						deptNameEq(condition.getDeptName()),
						employeeStatusEq(condition.getStatus())
				)
				.offset(pageable.getOffset())	// 현재페이지에 
				.limit(pageable.getPageSize()) // 몇개씩 보여줄지
				.fetch();					   // 해당조건으로 데이터 가져오기.
		// 전체 페이지 세는법
		JPAQuery<Long> countQuery = queryFactory
			.select(employee.count())	// 개수 세기
			.from(employee)
			.where(
					keywordContains(condition.getKeyword()),
					deptNameEq(condition.getDeptName()),
					employeeStatusEq(condition.getStatus())
					);	
			
		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
	}
	


	@Override
	// 고용형태 객체 필드에 :
	// - querydsl로 조회한 것을 고용형태 객체 빌더에 담아서 값을 전달
	public EmployeeStatsDto getEmployeeStats() {
		// 총 정규직 카운트
		// - null 일 수도 있음 , OrElse로 0전달
		// - querydsl의 count기능 사용.
		// - 고용형태가 정규직이고, 상태가 활성인것.
		Long regularCountResult = Optional.ofNullable(queryFactory.select(employee.count()) // 조회할것(카운트).
				.from(employee).where(employee.employeeType.eq("Regular"), employee.status.eq("ACTIVE")).fetchOne() // 실행버튼(단건)
		).orElse(0L);
		String regularCountStr = String.format("%,d", regularCountResult);
		// 계약직 카운트
		// - null일 수 있음
		// - count 사용
		// - 고용형태가 계약직이고, 상태가 활성인 것.
		Long contractResult = Optional
				.ofNullable(queryFactory.select(employee.count()).from(employee)
						.where(employee.employeeType.eq("Contract"), employee.status.eq("ACTIVE")).fetchOne())
				.orElse(0L);
		String contractCountStr = String.format("%,d", contractResult);
		// 평균 근속 연수
		// - 입사일이 존재하고, 활성상태인 직원중에서 계산.
		Double avgResult = Optional
				.ofNullable(queryFactory
						.select(employee.hireDate.year().longValue().subtract(LocalDate.now().getYear()).abs().avg())
						.from(employee).where(employee.status.eq("ACTIVE"), employee.hireDate.isNotNull()).fetchOne())
				.orElse(0.0);
		String avgWorkingYearStr = String.format("%.1f년", avgResult);

		return EmployeeStatsDto.builder().regularCount(regularCountStr).contractCount(contractCountStr)
				.avgWorkingYears(avgWorkingYearStr).growthRate("+12.4%").build();
	}

}