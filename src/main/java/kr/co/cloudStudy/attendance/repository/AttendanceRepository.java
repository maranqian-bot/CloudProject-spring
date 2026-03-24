package kr.co.cloudStudy.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.cloudStudy.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	
	List<Attendance> findAllByOrderByWorkDateDesc();
}
