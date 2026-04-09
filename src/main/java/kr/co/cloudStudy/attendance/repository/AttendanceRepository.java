package kr.co.cloudStudy.attendance.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.cloudStudy.attendance.entity.Attendance;
import kr.co.cloudStudy.attendance.enums.AttendanceStatus;


public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEmployee_EmployeeIdAndWorkDateBetween(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Attendance> findByEmployee_EmployeeIdOrderByWorkDateDesc(Long employeeId);

    Page<Attendance> findByEmployee_EmployeeIdOrderByWorkDateDesc(Long employeeId, Pageable pageable);

    Optional<Attendance> findByEmployee_EmployeeIdAndWorkDate(Long employeeId, LocalDate workDate);

    Optional<Attendance> findByAttendanceIdAndEmployee_EmployeeId(Long attendanceId, Long employeeId);

    long countByEmployee_EmployeeIdAndWorkDateBetweenAndAttendanceStatusIn(
            Long employeeId,
            LocalDate startDate,
            LocalDate endDate,
            Collection<AttendanceStatus> statuses
    );

    @Query("""
            select count(distinct a.employee.employeeId)
            from Attendance a
            where a.employee.department.departmentId = :departmentId
              and a.workDate = :workDate
              and a.employee.employeeId <> :employeeId
              and a.attendanceStatus in :statuses
            """)
    long countDistinctByDepartmentAndWorkDateAndStatusInExcludingEmployee(
    		@Param("departmentId") Long departmentId,
            @Param("workDate") LocalDate workDate,
            @Param("employeeId") Long employeeId,
            @Param("statuses") Collection<AttendanceStatus> statuses
    );
}