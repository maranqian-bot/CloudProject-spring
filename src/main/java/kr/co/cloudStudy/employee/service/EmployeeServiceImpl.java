package kr.co.cloudStudy.employee.service;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import kr.co.cloudStudy.attendance.repository.AttendanceRepository;
import kr.co.cloudStudy.department.entity.Department;
import kr.co.cloudStudy.department.repository.DepartmentRepository;
import kr.co.cloudStudy.employee.dto.EmployeeReqDto;
import kr.co.cloudStudy.employee.dto.EmployeeResDto;
import kr.co.cloudStudy.employee.dto.EmployeeSearchDto;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import kr.co.cloudStudy.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeRepository employeeRepository;
	private final DepartmentRepository departmentRepository;
	 private final AttendanceRepository attendanceRepository;
	 private final VacationRepository vacationRepository;
	
	// 직원 1명을 Id로 조회 -> 존재하는 경우 모든 정보를 불러오되 Enum 에러 방어
	 @Override
	 @Transactional(readOnly = true)
	 public EmployeeResDto getEmployeeDetail(Long employeeId) {
	     // 1. 직원 기본 정보 조회 및 DTO 생성
	     Employee employee = employeeRepository.findById(employeeId)
	             .orElseThrow(() -> new EntityNotFoundException("직원 없음"));
	     EmployeeResDto dto = EmployeeResDto.fromEntity(employee);

	     // 2. 근태 데이터 주입 (최신 10건)
	     dto.attendanceHistory = attendanceRepository
	             .findByEmployee_EmployeeIdOrderByWorkDateDesc(employeeId, PageRequest.of(0, 10))
	             .getContent().stream()
	             .map(a -> EmployeeResDto.AttendanceDetail.builder()
	                     .workDate(a.getWorkDate())
	                     .checkInTime(a.getCheckInTime())
	                     .checkOutTime(a.getCheckOutTime())
	                     .attendanceStatus(String.valueOf(a.getAttendanceStatus()))
	                     .build())
	             .collect(Collectors.toList());

	     // 3. 휴가 데이터 주입 (최신 5건)
	     dto.pendingVacation = vacationRepository
	             .findByEmployee_EmployeeNumberOrderByStartDateDesc(employee.getEmployeeNumber(), PageRequest.of(0, 5))
	             .getContent().stream()
	             .map(v -> EmployeeResDto.VacationDetail.builder()
	                     .vacationType(String.valueOf(v.getVacationType()))
	                     .startDate(v.getStartDate())
	                     .endDate(v.getEndDate())
	                     .vacationDays(v.getVacationDays())
	                     .vacationStatus(String.valueOf(v.getVacationStatus()))
	                     .build())
	             .collect(Collectors.toList());

	     return dto;
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