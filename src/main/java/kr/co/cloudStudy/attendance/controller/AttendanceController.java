package kr.co.cloudStudy.attendance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.attendance.dto.AttendanceHistoryResponseDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;
import kr.co.cloudStudy.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@RestController // api 응답을 보내는 컨트롤러
@RequestMapping("/api/employees/{employeeId}/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "직원 근태 조회 API")
public class AttendanceController {
	private final AttendanceService attendanceService;
	
	@Operation(
			summary = "직원 근태 요약 조회",
			description = "특정 직원의 이번 달 근태 요약 정보를 조회합니다."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "근태 요약 조회 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청값"),
			@ApiResponse(responseCode = "404", description = "직원을 찾을 수 없음"),
			@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@GetMapping("/summary") 
	public ResponseEntity<AttendanceSummaryResponseDTO> getAttendanceSummary(
			@PathVariable("employeeId") Long employeeId) {
		// 1. 근태 요약 조회
		// 데이터 가져오려면 서비스 -> 레포 거쳐야함
		AttendanceSummaryResponseDTO response = attendanceService.getAttendanceSummary(employeeId);
		return ResponseEntity.ok(response);
	}
	
	@Operation(
			summary = "직원 근태 이력 조회",
			description = "특정 직원의 근태 이력 목록을 최신 근무일 기준으로 조회합니다."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "근태 이력 조회 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청값"),
			@ApiResponse(responseCode = "404", description = "직원을 찾을 수 없음"),
			@ApiResponse(responseCode = "500", description = "서버 내부 오류")
	})
	@GetMapping("/history")
	public ResponseEntity<List<AttendanceHistoryResponseDTO>> getAttendanceHistory(
			@PathVariable("employeeId") Long employeeId) {
		// 2. 근태 이력 조회
		List<AttendanceHistoryResponseDTO> response = attendanceService.getAttendanceHistory(employeeId);
		return ResponseEntity.ok(response);
	}
	
	
}
