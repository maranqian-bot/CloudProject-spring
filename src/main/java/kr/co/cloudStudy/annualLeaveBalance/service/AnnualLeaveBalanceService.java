package kr.co.cloudStudy.annualLeaveBalance.service;

import kr.co.cloudStudy.vacation.dto.VacationManagementSummaryDTO;

public interface AnnualLeaveBalanceService {

    /**
     * 특정 직원의 특정 연도 연차 요약 정보 조회
     *
     * 해당 직원의 사용 가능 연차, 사용한 연차, 승인 대기 건수를 조회하여 반환한다.
     *
     * @param employeeNumber 조회할 직원 사번
     * @param year 조회할 연도
     * @return 휴가 관리 요약 정보 DTO
     */
    VacationManagementSummaryDTO getVacationManagementSummary(String employeeNumber, Integer year);
}