package kr.co.cloudStudy.department.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.department.dto.ResDeptDTO;
import kr.co.cloudStudy.department.entity.Department;
import kr.co.cloudStudy.department.repository.DepartmentRepository;
import kr.co.cloudStudy.department.service.DepartmentService;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;
	
	@Override
	@Transactional 
	public Long register(ReqDeptDTO dto) {
		// DTO 엔터티 변환 (빌더 사용 -> form() 호출로 변경)
		Department entity = Department.from(dto);
		
		if(dto.getManagerId() != null) {
			Employee manager = employeeRepository.findById(dto.getManagerId())
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 직원입니다. id=" + dto.getManagerId()));
			entity.updateManager(manager);
		}				
		// departmentRepository 저장 후 생성된 deptid 반환
		return departmentRepository.save(entity).getDeptid();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ResDeptDTO> getList(Pageable pageable){
		
		// 페이지 단위로 엔터티 조회
		Page<Department> result = departmentRepository.findAll(pageable);
		
		// 엔터티 리스트를 DTO 리스트로 변환 (Stream -> map 으로 변경)
		return result.map(entity -> ResDeptDTO.builder()
						.deptid(entity.getDeptid())
						.deptCode(entity.getDeptCode())
						.deptName(entity.getDeptName())
						.description(entity.getDescription())
						.createdAt(entity.getCreatedAt())
						.build());
	}
			
	@Override
	@Transactional(readOnly = true)
	public ResDeptDTO read(Long deptid) {
		
		// findById(id) : ID로 찾고 없으면 예외 발생 ("해당 부서가 없습니다.")
		Department entity = departmentRepository.findById(deptid)
				.orElseThrow(() -> new IllegalArgumentException("해당 부서가 없습니다. id=" + deptid));
		
		return ResDeptDTO.builder()
				.deptid(entity.getDeptid())
				.deptCode(entity.getDeptCode())
				.deptName(entity.getDeptName())
				.description(entity.getDescription())
				.createdAt(entity.getCreatedAt())
				.build();		
	}
	
	@Override
	@Transactional 
	public void modify(ReqDeptDTO dto) {
		
		Department entity = departmentRepository.findById(dto.getDeptid())
				.orElseThrow(() -> new IllegalArgumentException("해당 부서가 존재하지 않습니다. id=" + dto.getDeptid()));
		
		// 엔터티의 update() 호출 (자동 업데이트 반영)
		entity.update(dto);
	}
	
	@Override
	@Transactional 
	public void remove(Long deptid) {
		
		// 삭제하기 전 조회 후 삭제.  (없으면 예외 발생)
		Department entity = departmentRepository.findById(deptid)
				.orElseThrow(() -> new IllegalArgumentException("삭제할 부서가 없습니다. id=" +  deptid));
		
		// 위에서 조회한 부서 객체를 그대로 삭제 처리 (JPA 매커니즘 활용 : 영속화된 엔터티 객체를 전달 -> 삭제 수행)
		departmentRepository.delete(entity);
	}
		
}
	

	

