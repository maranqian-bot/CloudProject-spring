package kr.co.cloudStudy.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
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
	
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;	
	
	// 직원 1명을 Id로 조회 -> 존재하는 경우 모든 정보를 불러오되 Enum 에러 방어
	@Override
	@Transactional(readOnly = true)
	public EmployeeResDto getEmployeeDetail(Long employeeId) {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EntityNotFoundException("해당 직원을 찾을 수 없습니다."));
		
		try {
			// 정상적인 경우 상세 정보(근태, 휴가 포함) 반환
			return EmployeeResDto.fromEntity(employee);
		} catch (Exception e) {
			// EARLYLEAVE 등 Enum 에러 발생 시 기본 정보만이라도 반환하여 화면 멈춤 방지
			return EmployeeResDto.builder()
					.employeeId(employee.getEmployeeId())
					.name(employee.getName())
					.employeeNumber(employee.getEmployeeNumber())
					.email(employee.getEmail())
					.position(employee.getPosition())
					.departmentName(employee.getDepartment() != null ? employee.getDepartment().getDeptName() : "소속없음")
					.status(employee.getStatus())
					.build();
		}
	}
	
	// 직원 목록 조회 구현부 (가장 빈번한 400 에러 발생 지점 방어)
	@Override
	@Transactional(readOnly = true)
	public Page<EmployeeResDto> getEmployeeList(EmployeeSearchDto condition, Pageable pageable) {
		Page<Employee> employeePage = employeeRepository.searchEmployees(condition, pageable);
		
		return employeePage.map(employee -> {
			try {
				// 정상 변환 시도
				return EmployeeResDto.fromEntity(employee);
			} catch (Exception e) {
				// 리스트 중 한 명이라도 에러가 나면 해당 행만 기본 정보로 안전하게 대체
				return EmployeeResDto.builder()
						.employeeId(employee.getEmployeeId())
						.name(employee.getName())
						.employeeNumber(employee.getEmployeeNumber())
						.position(employee.getPosition())
						.departmentName(employee.getDepartment() != null ? 
									   employee.getDepartment().getDeptName() : "소속없음")
						.status(employee.getStatus())
						.build();
			}
		});
	}
	
	// 저장 구현부
	@Override
	@Transactional
	public EmployeeResDto saveEmployee(EmployeeReqDto employeeReqDto) {
		Department department = departmentRepository.findById(employeeReqDto.getDepartmentId()) 
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 부서 ID입니다."));
		
		Employee employee = employeeReqDto.toEntity(department);
		Employee savedEmployee = employeeRepository.save(employee);
		
		try {
			return EmployeeResDto.fromEntity(savedEmployee);
		} catch (Exception e) {
			return EmployeeResDto.builder()
					.employeeId(savedEmployee.getEmployeeId())
					.name(savedEmployee.getName())
					.employeeNumber(savedEmployee.getEmployeeNumber())
					.status(savedEmployee.getStatus())
					.build();
		}
	}
	
	// 수정 구현부
	@Override	
	@Transactional	 	
	public EmployeeResDto editEmployee(Long employeeId, Long departmentId, EmployeeReqDto employeeReqDto) {
		
		// 1. 직원 조회
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EntityNotFoundException("직원을 찾을 수 없습니다."));
		
		// 2. 부서 조회
		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new EntityNotFoundException("부서를 찾을 수 없습니다."));
		 
		// 3. 정보 갱신
		employee.updateEmployee(employeeReqDto, department); 
						 
		try {
			return EmployeeResDto.fromEntity(employee);
		} catch (Exception e) {
			return EmployeeResDto.builder()
					.employeeId(employee.getEmployeeId())
					.name(employee.getName())
					.employeeNumber(employee.getEmployeeNumber())
					.status(employee.getStatus())
					.build();
		}
	}
}