package kr.co.cloudStudy.attendance.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;
import kr.co.cloudStudy.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@RestController // api 응답을 보내는 컨트롤러
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
	private final AttendanceService attendanceService;
	
	@GetMapping("/summary")
	public ResponseEntity<AttendanceSummaryResponseDTO> getAttendanceSummary() {
		// 1. 근태 요약 조회
		// 데이터 가져오려면 서비스 -> 레포 거쳐야함
		AttendanceSummaryResponseDTO response = attendanceService.getAttendanceSummary();
		return ResponseEntity.ok(response);
	}
	
	
}
