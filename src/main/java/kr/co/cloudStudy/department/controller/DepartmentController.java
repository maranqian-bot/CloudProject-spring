package kr.co.cloudStudy.department.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.department.dto.ResDeptDTO;
import kr.co.cloudStudy.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department", description = "부서 관리 API")
public class DepartmentController {
	
	private final DepartmentService departmentService;
	
	// 부서 등록 (POST)
	@Operation(summary = "부서 등록" , description = "새로운 부서 정보를 시스템에 저장하고 생성된 PK(deptid)를 반환합니다.")
	@PostMapping
	public ResponseEntity<Long> register(@RequestBody ReqDeptDTO dto) {
		Long deptid = departmentService.register(dto);
		return ResponseEntity.ok(deptid);
	}
	
	// 부서 전체 목록 조회 (GET)
	@Operation(summary = "부서 목록 조회" , description = "등록된 모든 부서의 리스트를 조회합니다.")
	@GetMapping
	public ResponseEntity<Page<ResDeptDTO>> getList(
			@PageableDefault(page = 0, size = 5, sort = "deptid", direction = Sort.Direction.DESC) 
							 Pageable pageable) {
		
		return ResponseEntity.ok(departmentService.getList(pageable));
	}
	
	// 부서 상세 조회(GET)
	@Operation(summary = "부서 상세 조회", description = "부서 ID(deptid)를 통해 특정 부서 정보를 상세 조회합니다.")
	@GetMapping("/{deptid}")
	public ResponseEntity<ResDeptDTO> read(@PathVariable Long deptid) {
		ResDeptDTO dto = departmentService.read(deptid);
		return ResponseEntity.ok(dto);
	}
	
	// 부서 정보 수정 (PUT)
	@Operation(summary = "부서 정보 수정", description = "기존 부서의 정보(내용)를 업데이트합니다.")
	@PutMapping("/{deptid}")
	public ResponseEntity<Void> modify(
			@PathVariable("deptid") Long deptid,   // 주소의 번호를 꺼내서 deptid 변수 담기
			@RequestBody ReqDeptDTO dto            // 수정할 내용 DTO로 받기
	) {
		dto.setDeptid(deptid);					   // 주소에서 받은 ID -> DTO로 세팅하기
		
		departmentService.modify(dto);
		return ResponseEntity.ok().build();
	}
	
	// 부서 삭제 (DELETE)
	@Operation(summary = "부서 삭제", description = "부서 ID(deptid)를 이용해 해당 부서 데이터를 삭제합니다.")
	@DeleteMapping("/{deptid}")
	public ResponseEntity<Void> remove(@PathVariable Long deptid) {
		departmentService.remove(deptid);
		return ResponseEntity.ok().build();
	}
	
}
