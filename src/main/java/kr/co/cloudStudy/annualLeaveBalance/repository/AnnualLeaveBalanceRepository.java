package kr.co.cloudStudy.annualLeaveBalance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.cloudStudy.annualLeaveBalance.entity.AnnualLeaveBalance;

public interface AnnualLeaveBalanceRepository extends JpaRepository<AnnualLeaveBalance, Long> {

    // 특정 직원의 특정 연도 연차 보유 현황 조회
    Optional<AnnualLeaveBalance> findByEmployee_EmployeeNumberAndYear(String employeeNumber, Integer year);
}