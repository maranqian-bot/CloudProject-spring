package kr.co.cloudStudy.attendance.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

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
	public AttendanceSummaryResponseDTO getAttendanceSummary() {
		// 1. 이번 달 범위
		YearMonth currentMonth = YearMonth.now();
		LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();
        
		// 2. 이번 달 데이터 조회
        List<Attendance> attendanceList = attendanceRepository.findByWorkDateBetween(startDate, endDate);
		
        // 3. 출근 일수
        int workDays = (int) attendanceList.stream()
        		.filter(a ->
        				a.getAttendanceStatus() == AttendanceStatus.NORMAL ||
        				a.getAttendanceStatus() == AttendanceStatus.LATE ||
        				a.getAttendanceStatus() == AttendanceStatus.OVERTIME)
        		.count();
        
		// 4. 지각 횟수
        int lateCount = (int) attendanceList.stream()
        		.filter(a -> a.getAttendanceStatus() == AttendanceStatus.LATE)
        		.count();
        
		// 5. 결근
        int absentCount = (int) attendanceList.stream()
        		.filter(a -> a.getAttendanceStatus() == AttendanceStatus.VACATION)
        		.count();
        
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
	public List<AttendanceHistoryResponseDTO> getAttendanceHistory() {
		List<Attendance> attendanceList = attendanceRepository.findAll();
		
		return attendanceList.stream()
				.map(attendance -> AttendanceHistoryResponseDTO.builder()
						.id(attendance.getAttendanceId())
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
