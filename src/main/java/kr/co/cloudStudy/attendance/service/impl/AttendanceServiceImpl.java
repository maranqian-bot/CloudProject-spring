package kr.co.cloudStudy.attendance.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static kr.co.cloudStudy.global.utils.AttendanceUtils.*;

import org.springframework.stereotype.Service;

import kr.co.cloudStudy.attendance.dto.AttendanceHistoryResponseDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;
import kr.co.cloudStudy.attendance.entity.Attendance;
import kr.co.cloudStudy.attendance.entity.AttendanceStatus;
import kr.co.cloudStudy.attendance.repository.AttendanceRepository;
import kr.co.cloudStudy.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{
	private final AttendanceRepository attendanceRepository;
	
	// 1. summary 계산
	@Override
	public AttendanceSummaryResponseDTO getAttendanceSummary(Long employeeId) {
		// 1. 이번 달 범위
		YearMonth currentMonth = YearMonth.now();
		LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();
        
		// 2. 특정 직원의 이번 달 summary 조회
        List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdAndWorkDateBetween(employeeId, startDate, endDate);
		
        int workDays = 0;
        int lateCount = 0;
        int absentCount = 0;
        
        for (Attendance attendance : attendanceList) {
        	AttendanceStatus status = attendance.getAttendanceStatus();
        	
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
        
        
        
		// 6. 점수 계산
        double attendanceScore = calculateAttendanceScore(workDays, lateCount, absentCount);
        
		// 7. dto 반환
		return AttendanceSummaryResponseDTO.builder()
										   .workDays(workDays)
										   .lateCount(lateCount)
										   .absentCount(absentCount)
										   .attendanceScore(attendanceScore)
										   .build();
	}
	
	@Override
	public List<AttendanceHistoryResponseDTO> getAttendanceHistory(Long employeeId) {
		List<Attendance> attendanceList = attendanceRepository.findByEmployeeIdOrderByWorkDateDesc(employeeId);
		// "특정 직원"의 근태 기록을 조회해야 됨
		return attendanceList.stream()
				.map(attendance -> AttendanceHistoryResponseDTO.builder()
						.attendanceId(attendance.getAttendanceId())
						.employeeId(attendance.getEmployeeId())
						.workDate(attendance.getWorkDate())
						.checkInTime(attendance.getCheckInTime())
						.checkOutTime(attendance.getCheckOutTime())
						.workMinutes(attendance.getWorkMinutes())
						.attendanceStatus(attendance.getAttendanceStatus())
						.build())
				.toList();
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
}
