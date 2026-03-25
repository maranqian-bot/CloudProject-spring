package kr.co.cloudStudy.attendance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.cloudStudy.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	// 특정 직원의 이번 달 summary 계산용
	List<Attendance> findByEmployeeIdAndWorkDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
	
	// 특정 직원의 history 최신순 조회용
	List<Attendance> findByEmployeeIdOrderByWorkDateDesc(Long employeeId);
}
