package kr.co.cloudStudy.department.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.department.dto.ResDeptDTO;

/**
 * 부서 관리 서비스 인터페이스
 * 표준 CRUD 구성: 등록(register), 목록(getList), 상세(read), 수정(modify), 삭제(remove) 설계
 */
public interface DepartmentService {
	
	/**
	 * 신규 부서 등록
	 * @param dto 등록할 부서정보
	 * @return 생성된 부서의 PK(deptid)
	 */
	Long register(ReqDeptDTO dto);
	
	/**
	 * 전제 부서 목록 조회
	 * @return 부서 응답 DTO 리스트 입니다.
	 */
	Page<ResDeptDTO> getList(Pageable pageable);
	
	/**
	 * 부서 상세 조회
	 * @param deptid 조회할 부서 PK
	 * @return 부서 상세 정보 DTO
	 */
	ResDeptDTO read(Long deptid);
	
	/**
	 * 부서 정보 수정
	 * @param dto 수정할 부서 정보 (deptid 포함)
	 */
	void modify(ReqDeptDTO dto);
	
	/**
	 * 부서 삭제
	 * @param deptid 삭제할 부서 PK
	 */
	void remove(Long deptid);
		
}
