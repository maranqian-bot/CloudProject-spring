package kr.co.cloudStudy.department.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.department.dto.ResDeptDTO;
import kr.co.cloudStudy.department.entity.Department;
import kr.co.cloudStudy.department.repository.DepartRepository;
import kr.co.cloudStudy.department.service.DepartService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartServiceImpl implements DepartService {
	
	private final DepartRepository departRepository;
	
	@Override
	@Transactional 
	public Long register(ReqDeptDTO dto) {
		// DTO 엔터티 변환 (빌더 사용)
		Department entity = Department.builder()
				.deptCode(dto.getDeptCode())
				.deptName(dto.getDeptName())
				.description(dto.getDescription())
				.build();
		
		// departRepository 저장 후 생성된 ID 반환
		return departRepository.save(entity).getDeptid();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ResDeptDTO> getList() {
		
		// 모든 엔터티 조회
		List<Department> result = departRepository.findAll();
		
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
		Department entity = departRepository.findById(id)
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
		
		Department entity = departRepository.findById(dto.getDeptid())
				.orElseThrow(() -> new IllegalArgumentException("해당 부서가 존재하지 않습니다. id=" + dto.getDeptid()));
		
		// 수정 시 엔터티 내용 변경 (Dirty Checking 활용)
		entity.setDeptName(dto.getDeptName());
		entity.setDescription(dto.getDescription());
	}
	
	@Override
	@Transactional 
	public void remove(Long deptid) {
		
		// 삭제 할 대상이 있는지 확인하는 장치
		if(!departRepository.existsById(deptid)) {
			throw new IllegalArgumentException("삭제할 부서가 존재하지 않습니다. id=" + deptid);
		}
		
		// 부서 ID를 이용해 바로 삭제 실행
		departRepository.deleteById(deptid);
	}
		
}
	

	

