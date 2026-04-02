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

    // 전체 대기 중인 휴가 신청 목록 조회 (최신순)
    List<Vacation> findByVacationStatusOrderByCreatedAtDesc(VacationStatus vacationStatus);

    // 특정 승인자의 대기 중인 휴가 신청 목록 조회 (employee fetch join)
    @Query("""
            select v
            from Vacation v
            join fetch v.employee e
            where v.approver.employeeId = :approverId
              and v.vacationStatus = :status
            order by v.createdAt desc
            """)
    List<Vacation> findPendingApprovalsWithEmployee(
            @Param("approverId") Long approverId,
            @Param("status") VacationStatus status
    );

    @Query(
            value = """
                    select v
                    from Vacation v
                    join fetch v.employee e
                    join fetch e.department d
                    where v.approver.employeeNumber = :approverEmployeeNumber
                      and (:vacationType is null or v.vacationType = :vacationType)
                    order by v.createdAt desc
                    """,
            countQuery = """
                    select count(v)
                    from Vacation v
                    where v.approver.employeeNumber = :approverEmployeeNumber
                      and (:vacationType is null or v.vacationType = :vacationType)
                    """
    )
    Page<Vacation> findVacationRequestList(
            @Param("approverEmployeeNumber") String approverEmployeeNumber,
            @Param("vacationType") VacationType vacationType,
            Pageable pageable
    );

    @Query("""
            select v
            from Vacation v
            join fetch v.employee e
            join fetch e.department d
            left join fetch v.approver a
            where v.vacationId = :vacationId
            """)
    Optional<Vacation> findDetailByVacationId(@Param("vacationId") Long vacationId);

    long countByApprover_EmployeeNumberAndVacationStatus(
            String approverEmployeeNumber,
            VacationStatus vacationStatus
    );

    @Query("""
            select count(distinct v.employee.employeeId)
            from Vacation v
            where v.approver.employeeNumber = :approverEmployeeNumber
              and v.startDate between :startDate and :endDate
            """)
    long countDistinctEmployeesByApproverAndStartDateBetween(
            @Param("approverEmployeeNumber") String approverEmployeeNumber,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}