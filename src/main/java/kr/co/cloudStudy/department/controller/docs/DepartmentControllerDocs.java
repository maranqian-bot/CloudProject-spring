package kr.co.cloudStudy.department.controller.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.department.dto.ResDeptDTO;

@Tag(name = "Department", description = "부서 관리 API")
public interface DepartmentControllerDocs {

	@Operation(summary = "부서 등록", description = "새로운 부서 정보를 저장하고 생성된 PK를 반환합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "등록 성공")
    })
    ResponseEntity<Long> register(ReqDeptDTO dto);

    @Operation(summary = "부서 목록 조회", description = "등록된 모든 부서의 리스트를 조회합니다.")
    ResponseEntity<Page<ResDeptDTO>> getList(Pageable pageable);

    @Operation(summary = "부서 상세 조회", description = "부서 ID를 통해 특정 부서 정보를 상세 조회합니다.")
    ResponseEntity<ResDeptDTO> read(
        @Parameter(description = "조회할 부서 ID") Long departmentId
    );

    @Operation(summary = "부서 정보 수정", description = "기존 부서 정보를 업데이트합니다.")
    ResponseEntity<ResDeptDTO> modify(
        @Parameter(description = "수정할 부서 ID") Long departmentId, 
        ReqDeptDTO dto
    );

    @Operation(summary = "부서 삭제", description = "부서 ID를 이용해 해당 데이터를 삭제합니다.")
    ResponseEntity<Void> remove(
        @Parameter(description = "삭제할 부서 ID") Long departmentId
    );
}
