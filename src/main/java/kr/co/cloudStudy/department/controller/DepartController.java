package kr.co.cloudStudy.department.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.department.dto.ResDeptDTO;
import kr.co.cloudStudy.department.service.DepartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Department", description = "부서 관리 API")
public class DepartController {
	
	private final DepartService departService;
	
	// 부서 등록 (POST)
	@PostMapping
	public ResponseEntity<Long> register(@RequestBody ReqDeptDTO dto) {
		Long id = departService.register(dto);
		return ResponseEntity.ok(id);
	}
	
	// 부서 전체 목록 조회 (GET)
	@GetMapping
	public ResponseEntity<List<ResDeptDTO>> getList() {
		List<ResDeptDTO> list = departService.getList();
		return ResponseEntity.ok(list);
	}
	
	// 부서 상세 조회(GET)
	@GetMapping("/{id}")
	public ResponseEntity<ResDeptDTO> read(@PathVariable Long id) {
		ResDeptDTO dto = departService.read(id);
		return ResponseEntity.ok(dto);
	}
	
	// 부서 정보 수정 (PUT)
	@PutMapping
	public ResponseEntity<Void> modify(@RequestBody ReqDeptDTO dto) {
		departService.modify(dto);
		return ResponseEntity.ok().build();
	}
	
	// 부서 삭제 (DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		departService.remove(id);
		return ResponseEntity.ok().build();
	}
	
}
