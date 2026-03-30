package kr.co.cloudStudy.attendance.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.co.cloudStudy.attendance.dto.AttendanceHistoryResponseDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;

public interface AttendanceControllerDocs {

	@Operation(
			summary = "직원 근태 요약 조회",
			description = "특정 직원의 이번 달 근태 요약 정보를 조회합니다."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", 
						 description = "근태 요약 조회 성공",
						 content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = ApiResponseDTO.class)
				)
			), 
			@ApiResponse(responseCode = "400", 
						 description = "잘못된 요청값",
						 content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = ApiResponseDTO.class)
						)
						 
			),
			@ApiResponse(responseCode = "404", 
						 description = "직원을 찾을 수 없음",
						 content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = ApiResponseDTO.class)
						)
			),
			@ApiResponse(responseCode = "500", 
						 description = "서버 내부 오류",
						 content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = ApiResponseDTO.class)
						)
						 
			)
	})
	public ResponseEntity<ApiResponseDTO<AttendanceSummaryResponseDTO>> getAttendanceSummary(Long employeeId);
	
	@Operation(
			summary = "직원 근태 이력 조회",
			description = "특정 직원의 근태 이력 목록을 최신 근무일 기준으로 조회합니다."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", 
						 description = "근태 이력 조회 성공",
						 content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = ApiResponseDTO.class)
						)
			),
			@ApiResponse(responseCode = "400", 
						 description = "잘못된 요청값",
						 content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = ApiResponseDTO.class)
						)
			),
			@ApiResponse(responseCode = "404", 
				     	 description = "직원을 찾을 수 없음",
				     	 content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = ApiResponseDTO.class)
						)
			),
			@ApiResponse(responseCode = "500",
						description = "서버 내부 오류",
						content = @Content(
								mediaType = "application/json",
								schema = @Schema(implementation = ApiResponseDTO.class)
						)
			)
	})
	public ResponseEntity<ApiResponseDTO<List<AttendanceHistoryResponseDTO>>> getAttendanceHistory(Long employeeId);
	
	@Operation(
            summary = "직원 근태 엑셀 다운로드",
            description = "특정 직원의 근태 이력을 엑셀 파일로 다운로드합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "엑셀 다운로드 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청값"),
            @ApiResponse(responseCode = "404", description = "직원을 찾을 수 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
	public ResponseEntity<StreamingResponseBody> downloadAttendanceExcel(Long employeeId);
}
 