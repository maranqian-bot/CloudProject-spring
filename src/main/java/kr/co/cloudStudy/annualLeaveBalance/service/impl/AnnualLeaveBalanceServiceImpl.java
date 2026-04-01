package kr.co.cloudStudy.annualLeaveBalance.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.annualLeaveBalance.entity.AnnualLeaveBalance;
import kr.co.cloudStudy.annualLeaveBalance.repository.AnnualLeaveBalanceRepository;
import kr.co.cloudStudy.annualLeaveBalance.service.AnnualLeaveBalanceService;
import kr.co.cloudStudy.vacation.dto.VacationManagementSummaryDTO;
import kr.co.cloudStudy.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnualLeaveBalanceServiceImpl implements AnnualLeaveBalanceService {

    private final AnnualLeaveBalanceRepository annualLeaveBalanceRepository;
    private final VacationRepository vacationRepository;

    @Override
    public VacationManagementSummaryDTO getVacationManagementSummary(String employeeNumber, Integer year) {
        validateEmployeeNumber(employeeNumber);
        validateYear(year);

        AnnualLeaveBalance leaveBalance = annualLeaveBalanceRepository
                .findByEmployee_EmployeeNumberAndYear(employeeNumber, year)
                .orElse(null);

        BigDecimal availableVacationDays = leaveBalance != null
                ? leaveBalance.getRemainingDays()
                : BigDecimal.ZERO;

        BigDecimal usedVacationDays = leaveBalance != null
                ? leaveBalance.getUsedDays()
                : BigDecimal.ZERO;

        long pendingApprovalCount = vacationRepository
                .countByEmployee_EmployeeNumberAndVacationStatus(employeeNumber, "PENDING");

        return VacationManagementSummaryDTO.builder()
                .availableVacationDays(availableVacationDays)
                .usedVacationDays(usedVacationDays)
                .pendingApprovalCount(pendingApprovalCount)
                .build();
    }

    private void validateEmployeeNumber(String employeeNumber) {
        if (employeeNumber == null || employeeNumber.isBlank()) {
            throw new IllegalArgumentException("직원 사번은 필수입니다.");
        }
    }

    private void validateYear(Integer year) {
        if (year == null) {
            throw new IllegalArgumentException("연도는 필수입니다.");
        }

        if (year < 2000) {
            throw new IllegalArgumentException("연도 값이 올바르지 않습니다.");
        }
    }
}