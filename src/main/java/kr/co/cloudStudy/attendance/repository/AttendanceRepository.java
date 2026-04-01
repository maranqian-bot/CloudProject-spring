package kr.co.cloudStudy.attendance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.cloudStudy.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	List<Attendance> findByEmployee_EmployeeIdAndWorkDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
	
	List<Attendance> findByEmployee_EmployeeIdOrderByWorkDateDesc(Long employeeId);
	
	Page<Attendance> findByEmployee_EmployeeIdOrderByWorkDateDesc(Long employeeId, Pageable pageable);
	
}
