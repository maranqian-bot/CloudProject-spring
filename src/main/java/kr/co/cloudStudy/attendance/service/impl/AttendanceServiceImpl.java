package kr.co.cloudStudy.attendance.service.impl;

import org.springframework.stereotype.Service;

import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;
import kr.co.cloudStudy.attendance.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService{
	
	@Override
	public AttendanceSummaryResponseDTO getAttendanceSummary() {
		return AttendanceSummaryResponseDTO.builder()
										   .workDays(20)
										   .lateCount(2)
										   .absentCount(1)
										   .absentCount(0)
										   .attendanceScore(98.8)
										   .build();
	}
}
