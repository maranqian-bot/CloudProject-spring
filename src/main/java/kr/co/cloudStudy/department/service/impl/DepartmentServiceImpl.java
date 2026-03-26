package kr.co.cloudStudy.department.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.department.dto.ResDeptDTO;
import kr.co.cloudStudy.department.entity.Department;
import kr.co.cloudStudy.department.repository.DepartmentRepository;
import kr.co.cloudStudy.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	
	@Override
	@Transactional 
	public Long register(ReqDeptDTO dto) {
		// DTO 엔터티 변환 (빌더 사용 -> form() 호출로 변경)
		Department entity = Department.from(dto);
				
		// departmentRepository 저장 후 생성된 deptid 반환
		return departmentRepository.save(entity).getDeptid();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ResDeptDTO> getList() {
		
		// 모든 엔터티 조회
		List<Department> result = departmentRepository.findAll();
		
		// 엔터티 리스트를 DTO 리스트로 변환 (Stream 활용)
		return result.stream()
				.map(entity -> ResDeptDTO.builder()
						.deptid(entity.getDeptid())
						.deptCode(entity.getDeptCode())
						.deptName(entity.getDeptName())
						.description(entity.getDescription())
						.createdAt(entity.getCreatedAt())
						.build())
				.collect(Collectors.toList());
	}
			
	@Override
	@Transactional(readOnly = true)
	public ResDeptDTO read(Long id) {
		
		// findById(id) : ID로 찾고 없으면 예외 발생 ("해당 부서가 없습니다.")
		Department entity = departmentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 부서가 없습니다. id=" + id));
		
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
	

	

