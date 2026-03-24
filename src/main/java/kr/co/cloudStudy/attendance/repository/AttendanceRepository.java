package kr.co.cloudStudy.attendance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.cloudStudy.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	// 이번 달 summary 계산용
	List<Attendance> findByWorkDateBetween(LocalDate startDate, LocalDate endDate);
	
	// history 최신순 조회용
	// attendance 엔티티에서 모두 가져오겠다 workdate를
	List<Attendance> findAllByOrderByWorkDateDesc();
}
