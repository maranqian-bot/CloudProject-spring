package kr.co.cloudStudy.attendance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.cloudStudy.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	/**
	 * 특정 직원의 이번 달 summary 꼐산용
	 * 
	 * @param employeeId 조회할 직원 ID
	 * @param startDate 이번 달 시작일
	 * @param endDate 이번 달 종료일
	 * @return 
	 */
	List<Attendance> findByEmployeeIdAndWorkDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
	
	/**
	 * 특정 직원의 이번 달 최신순 근태 이력
	 * @param employeeId 조회할 직원 ID
	 * @return 이번달 특정 직원의 근태 리스트
	 */
	List<Attendance> findByEmployeeIdOrderByWorkDateDesc(Long employeeId);
}
