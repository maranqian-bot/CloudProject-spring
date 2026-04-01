package kr.co.cloudStudy.vacation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    // 특정 직원의 휴가 신청 이력 조회 (최신순)
    List<Vacation> findByEmployee_EmployeeNumberOrderByCreatedAtDesc(String employeeNumber);

    // 특정 직원의 대기 중인 휴가 신청 건수 조회
    long countByEmployee_EmployeeNumberAndVacationStatus(String employeeNumber, VacationStatus vacationStatus);

    // 전체 대기 중인 휴가 신청 목록 조회 (최신순)
    List<Vacation> findByVacationStatusOrderByCreatedAtDesc(VacationStatus vacationStatus);

    // 특정 승인자의 대기 중인 휴가 신청 목록 조회 (최신순)
    List<Vacation> findByApprover_EmployeeIdAndVacationStatusOrderByCreatedAtDesc(Long approverId, VacationStatus vacationStatus);
}