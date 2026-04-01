package kr.co.cloudStudy.vacation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    // 특정 직원의 휴가 신청 이력 조회 (최신순)
    List<Vacation> findByEmployee_EmployeeNumberOrderByCreatedAtDesc(String employeeNumber);

    // 특정 직원의 대기 중인 휴가 신청 건수 조회
    long countByEmployee_EmployeeNumberAndVacationStatus(String employeeNumber, VacationStatus vacationStatus);

    // 전체 대기 중인 휴가 신청 목록 조회 (최신순)
    List<Vacation> findByVacationStatusOrderByCreatedAtDesc(VacationStatus vacationStatus);

    // 특정 승인자의 대기 중인 휴가 신청 목록 조회 (employee fetch join)
    @Query("""
            select v
            from Vacation v
            join fetch v.employee e
            where v.approver.employeeId = :approverId
              and v.vacationStatus = :vacationStatus
            order by v.createdAt desc
            """)
    List<Vacation> findPendingApprovalsWithEmployee(Long approverId, VacationStatus vacationStatus);
}