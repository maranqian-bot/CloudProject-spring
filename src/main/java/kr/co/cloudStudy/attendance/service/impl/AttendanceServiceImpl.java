package kr.co.cloudStudy.attendance.service.impl;

import static kr.co.cloudStudy.global.utils.AttendanceStatusUtil.isAbsent;
import static kr.co.cloudStudy.global.utils.AttendanceStatusUtil.isLate;
import static kr.co.cloudStudy.global.utils.AttendanceStatusUtil.isWorkDays;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.cloudStudy.attendance.dto.AttendanceExcelRowDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceHistoryResponseDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;
import kr.co.cloudStudy.attendance.entity.Attendance;
import kr.co.cloudStudy.attendance.enums.AttendanceStatus;
import kr.co.cloudStudy.attendance.repository.AttendanceRepository;
import kr.co.cloudStudy.attendance.service.AttendanceService;
import kr.co.cloudStudy.global.exception.EmployeeNotFoundException;
import kr.co.cloudStudy.global.utils.AttendanceExcelUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
	private final AttendanceRepository attendanceRepository;
	

	@Override
	public AttendanceSummaryResponseDTO getAttendanceSummary(Long employeeId) {
		validateEmployeeId(employeeId);
		
		YearMonth currentMonth = YearMonth.now();
		LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();
        
        List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdAndWorkDateBetween(employeeId, startDate, endDate);
		
        int workDays = 0;
        int lateCount = 0;
        int absentCount = 0;
        
        for (Attendance attendance : attendanceList) {
        	AttendanceStatus status = attendance.getAttendanceStatus();
        	if (status == null) {
        		continue;
        	}
        	
        	if (isWorkDays(status)) {
        		workDays++;
        	}
        	
        	if (isLate(status)) {
        		lateCount++;
        	}
        	
        	if (isAbsent(status)) {
        		absentCount++;
        	}
        }
        
        double attendanceScore = calculateAttendanceScore(workDays, lateCount, absentCount);
        
		return AttendanceSummaryResponseDTO.builder()
										   .workDays(workDays)
										   .lateCount(lateCount)
										   .absentCount(absentCount)
										   .attendanceScore(attendanceScore)
										   .build();
	}
	
	@Override
	public List<AttendanceHistoryResponseDTO> getAttendanceHistory(Long employeeId) {
		validateEmployeeId(employeeId);
		
		List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdOrderByWorkDateDesc(employeeId);

		return attendanceList.stream()
				.map(attendance -> AttendanceHistoryResponseDTO.builder()
						.attendanceId(attendance.getAttendanceId())
						.employeeId(attendance.getEmpId())
						.workDate(attendance.getWorkDate())
						.checkInTime(attendance.getCheckInTime())
						.checkOutTime(attendance.getCheckOutTime())
						.workMinutes(attendance.getWorkMinutes())
						.attendanceStatus(attendance.getAttendanceStatus())
						.build())
				.toList();
	}
	
	
	@Override
	public void writeAttendanceExcel(Long employeeId, OutputStream outputStream) {
		validateEmployeeId(employeeId);
		
		List<AttendanceHistoryResponseDTO> historyList = getAttendanceHistory(employeeId);
		List<AttendanceExcelRowDTO> excelRows = convertToExcelRows(historyList);
		
		AttendanceExcelUtil.writeAttendanceExcel(excelRows, outputStream);
	}
	
	
	private List<AttendanceExcelRowDTO> convertToExcelRows(List<AttendanceHistoryResponseDTO> historyList) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		
		return historyList.stream()
				.map(item -> AttendanceExcelRowDTO.builder()
						.workDate(item.getWorkDate() != null ? item.getWorkDate().format(dateFormatter) : "")
						.checkInTime(item.getCheckInTime() != null ? item.getCheckInTime().format(timeFormatter) : "")
						.checkOutTime(item.getCheckOutTime() != null ? item.getCheckOutTime().format(timeFormatter) : "")
						.workMinutes(item.getWorkMinutes())
						.attendanceStatusLabel(toStatusLabel(item.getAttendanceStatus()))
						.build())
				.toList();
	}
	
	private String toStatusLabel(AttendanceStatus status) {
	    if (status == null) {
	        return "";
	    }

	    return switch (status) {
	        case NORMAL -> "정상";
	        case LATE -> "지각";
	        case EARLY_LEAVE -> "조퇴";
	        case VACATION -> "휴가";
	        case OVER_TIME -> "연장 근무";
	        default -> throw new IllegalArgumentException("Unexpected value: " + status);
	    };
	}
	
	private double calculateAttendanceScore(long workDays, long lateCount, long absentCount) {
		double score = 100.0;
		
		score -= lateCount * 2;
		score -= absentCount * 5;
		
		if (score < 0) {
			score = 0;
		}
		
		return score;
	}
	
	private void validateEmployeeId(Long employeeId) {
		if (employeeId == null) {
			throw new IllegalArgumentException("직원 ID는 필수입니다.");
		}
		
		if (employeeId <= 0) {
			throw new IllegalArgumentException("직원 ID는 1 이상이어야 합니다.");
		}
	}
	
//	private List<Attendance> getAttendanceListOrThrow(Long employeeId, LocalDate startDate, LocalDate endDate) {
//		List<Attendance> attendanceList = 
//				attendanceRepository.findByEmployeeIdAndWorkDateBetween(employeeId, startDate, endDate);
//		
//		if (attendanceList.isEmpty()) {
//			throw new EmployeeNotFoundException("해당 직원의 이번 달 근태 정보가 없습니다. employeeId=" + employeeId);
//		}
//		
//		return attendanceList;
//	}
//	
//	private List<Attendance> getAttendanceHistoryListOrThrow(Long employeeId) {
//	    List<Attendance> attendanceList =
//	            attendanceRepository.findByEmployeeIdOrderByWorkDateDesc(employeeId);
//
//	    if (attendanceList.isEmpty()) {
//	        throw new EmployeeNotFoundException("해당 직원의 근태 이력이 없습니다. employeeId=" + employeeId);
//	    }
//
//	    return attendanceList;
//	}
	
	
}
