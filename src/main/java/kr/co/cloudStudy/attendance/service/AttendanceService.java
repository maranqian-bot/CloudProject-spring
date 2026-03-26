package kr.co.cloudStudy.attendance.service;

import java.io.OutputStream;
import java.util.List;

import kr.co.cloudStudy.attendance.dto.AttendanceExcelRowDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceHistoryResponseDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;

public interface AttendanceService {
	
	AttendanceSummaryResponseDTO getAttendanceSummary(Long employeeId);
	
	List<AttendanceHistoryResponseDTO> getAttendanceHistory(Long employeeId);
	
	void writeAttendanceExcel(Long employeeId, OutputStream outputStream);
}
