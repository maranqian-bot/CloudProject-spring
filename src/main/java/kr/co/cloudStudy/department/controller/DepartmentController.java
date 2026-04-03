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
import kr.co.cloudStudy.department.controller.docs.DepartmentControllerDocs;
import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.department.dto.ResDeptDTO;
import kr.co.cloudStudy.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department", description = "부서 관리 API")
public class DepartmentController implements DepartmentControllerDocs {
	
	private final DepartmentService departmentService;
	
	// 부서 등록 (POST)
	@Override
	@PostMapping
	public ResponseEntity<Long> register(@RequestBody ReqDeptDTO dto) {
		Long departmentId = departmentService.register(dto);
		return ResponseEntity.ok(departmentId);
	}
	
	// 부서 전체 목록 조회 (GET)
	@Override
	@GetMapping
	public ResponseEntity<Page<ResDeptDTO>> getList(
			@PageableDefault(page = 0, size = 5, sort = "departmentId", direction = Sort.Direction.DESC) 
							 Pageable pageable) {
		
		return ResponseEntity.ok(departmentService.getList(pageable));
	}
	
	// 부서 상세 조회(GET)
	@Override
	@GetMapping("/{departmentId}")
	public ResponseEntity<ResDeptDTO> read(@PathVariable Long departmentId) {
		ResDeptDTO dto = departmentService.read(departmentId);
		return ResponseEntity.ok(dto);
	}
	
	// 부서 정보 수정 (PUT)
	@Override
	@PutMapping("/{departmentId}")
	public ResponseEntity<Void> modify(
			@PathVariable("departmentId") Long departmentId,   // 주소의 번호를 꺼내서 departmentId 변수 담기
			@RequestBody ReqDeptDTO dto            // 수정할 내용 DTO로 받기
	) {
		dto.setDepartmentId(departmentId);					   // 주소에서 받은 ID -> DTO로 세팅하기
		
		departmentService.modify(dto);
		return ResponseEntity.ok().build();
	}
	
	// 부서 삭제 (DELETE)
	@Override
	@DeleteMapping("/{departmentId}")
	public ResponseEntity<Void> remove(@PathVariable Long departmentId) {
		departmentService.remove(departmentId);
		return ResponseEntity.ok().build();
	}
	
}
