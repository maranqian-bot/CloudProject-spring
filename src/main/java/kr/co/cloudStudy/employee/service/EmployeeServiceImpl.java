package kr.co.cloudStudy.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.department.entity.Department;
import kr.co.cloudStudy.department.repository.DepartmentRepository;
import kr.co.cloudStudy.employee.dto.EmployeeReqDto;
import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository employeeRepository;	// 인터페이스 받아오는 곳
	private final DepartmentRepository departmentRepository;	
	
	// 조회 구현부
	@Override
	public Page<EmployeeResDto> getEmployeeList(EmployeeSearchDto condition, Pageable pageable) {	// 검색조건과, 페이지 객체를 받고
		return employeeRepository.searchEmployees(condition, pageable)	// 매개변수로 받은거 적용하여서 데이터 검색해줌
				.map(EmployeeResDto :: fromEntity);		// 검색해온 데이터에 대해 일단 dto변환 함수만 가져온다. 그리고 대기.
	};
	
	// 저장 구현부
	//	- 1. 직원추가 요청오면 -> 요청온 거 entity타입으로 변환
	//	- 2. entity타입으로 변환 했으니, 저장하기(직원추가)
	//	- 3. 저장 끝났으니 결과 응답을 위해서 -> entity -> resDto 변환하여 화면에 보여줄 값 보내주기
	
	@Override
	@Transactional
	public EmployeeResDto saveEmployee(EmployeeReqDto employeeReqDto) {
		Department department = departmentRepository.findById(employeeReqDto.getDepartmentId()) 
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부서 ID입니다."));
			 Employee employee = employeeReqDto.toEntity(department);	// 요청온 값 엔티티로 변환
			 Employee savedEmployee = employeeRepository.save(employee);		// 엔티티를 저장함 - 변수명 : saveEmployee
			 // 엔티티에서 -> 응답Dto로변환 후 컨트롤러로 응답dto를 리턴
			 return EmployeeResDto.fromEntity(savedEmployee);	
			 
		
	}
	
	// 조회해서 있으면? 에러내고 / 없으면 ? 수정 반영해주기(reqDto로 들어온거 엔티티 필드에 반영)
	// 조회될려면? 이미 존재하는 employee 엔티티여야하고, 아이디를 배정 받은 부서여야함
	@Override
	@Transactional
	public EmployeeResDto editEmployee(Long id, EmployeeReqDto employeeReqDto) {
		
		// employee 엔티티 확인
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("직원 없음"));
		// id 배정받은 부서인지 확인
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("부서 없음"));
		
		// 위의 거 모두 통과하면? -> 값 갱신해주기 -> 갱신하는역할? employeeUpdate(); <- 요청객체 + 부서객체
		employee.updateEmployee(employeeReqDto, department);	// 여기서 갱신이 된다.
		// 갱신됐으니깐? 엔티티를 -> 응답객체로 바꾸어주기 : 메서드(fromEntity)
		
		
		return EmployeeResDto.fromEntity(employee);	//	여기서 응답객체로 반환됨. 
	}
}



