package kr.co.cloudStudy.vacation.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;
import kr.co.cloudStudy.vacation.entity.VacationType;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    // 특정 직원의 휴가 신청 이력 조회 (최신순)
    List<Vacation> findByEmployee_EmployeeNumberOrderByCreatedAtDesc(String employeeNumber);

    // 특정 직원의 대기 중인 휴가 신청 건수 조회
    long countByEmployee_EmployeeNumberAndVacationStatus(String employeeNumber, VacationStatus vacationStatus);

    // 특정 승인자의 상태별 건수 조회
    long countByApprover_EmployeeNumberAndVacationStatus(String approverEmployeeNumber, VacationStatus vacationStatus);

    // 승인 대기 목록 조회
    @Query("""
            select v
            from Vacation v
            join fetch v.employee e
            where v.approver.employeeNumber = :approverEmployeeNumber
              and v.vacationStatus = :vacationStatus
            order by v.createdAt desc
            """)
    List<Vacation> findPendingApprovalsWithEmployee(
            @Param("approverEmployeeNumber") String approverEmployeeNumber,
            @Param("vacationStatus") VacationStatus vacationStatus
    );

    // 대시보드용 승인 완료 휴가 조회
    @Query("""
            select v
            from Vacation v
            join fetch v.employee e
            where v.employee.employeeNumber = :employeeNumber
              and v.vacationStatus = :vacationStatus
              and v.startDate between :startDate and :endDate
            order by v.createdAt desc
            """)
    List<Vacation> findApprovedDashboardVacationsWithEmployee(
            @Param("employeeNumber") String employeeNumber,
            @Param("vacationStatus") VacationStatus vacationStatus,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 휴가 신청 목록 페이지 조회
    @Query("""
            select v
            from Vacation v
            join fetch v.employee e
            where v.approver.employeeNumber = :approverEmployeeNumber
              and (:vacationType is null or v.vacationType = :vacationType)
            order by v.createdAt desc
            """)
    Page<Vacation> findVacationRequestList(
            @Param("approverEmployeeNumber") String approverEmployeeNumber,
            @Param("vacationType") VacationType vacationType,
            Pageable pageable
    );

    // 이번 달 휴가자 수 조회 (중복 직원 제거)
    @Query("""
            select count(distinct v.employee.employeeNumber)
            from Vacation v
            where v.approver.employeeNumber = :approverEmployeeNumber
              and v.vacationStatus = 'APPROVED'
              and v.startDate between :startDate and :endDate
            """)
    long countDistinctEmployeesByApproverAndStartDateBetween(
            @Param("approverEmployeeNumber") String approverEmployeeNumber,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // 상세 조회 (employee, approver 함께 조회)
    @Query("""
            select v
            from Vacation v
            join fetch v.employee e
            left join fetch v.approver a
            where v.vacationId = :vacationId
            """)
    Optional<Vacation> findDetailByVacationId(@Param("vacationId") Long vacationId);

    // 기본 조회
    Optional<Vacation> findByVacationId(Long vacationId);
}