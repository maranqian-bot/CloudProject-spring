package kr.co.cloudStudy.vacation.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.annualLeaveBalance.service.AnnualLeaveBalanceService;
import kr.co.cloudStudy.vacation.dto.MyVacationHistoryDTO;
import kr.co.cloudStudy.vacation.dto.PendingVacationApprovalDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementSummaryDTO;
import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;
import kr.co.cloudStudy.vacation.repository.VacationRepository;
import kr.co.cloudStudy.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;
    private final AnnualLeaveBalanceService annualLeaveBalanceService;

    @Override
    public List<MyVacationHistoryDTO> getMyVacationHistory(String employeeNumber) {
        validateEmployeeNumber(employeeNumber);

        List<Vacation> vacationList =
                vacationRepository.findByEmployee_EmployeeNumberOrderByCreatedAtDesc(employeeNumber);

        return vacationList.stream()
                .map(MyVacationHistoryDTO::from)
                .toList();
    }

    @Override
    public List<PendingVacationApprovalDTO> getPendingApprovals(Long approverId) {
        validateApproverId(approverId);

        List<Vacation> vacationList =
                vacationRepository.findPendingApprovalsWithEmployee(approverId, VacationStatus.PENDING);

        return vacationList.stream()
                .map(PendingVacationApprovalDTO::from)
                .toList();
    }

    @Override
    public VacationManagementResponseDTO getVacationManagementPage(String employeeNumber, Long approverId, Integer year) {
        VacationManagementSummaryDTO summary =
                annualLeaveBalanceService.getVacationManagementSummary(employeeNumber, year);

        List<MyVacationHistoryDTO> myVacationHistories = getMyVacationHistory(employeeNumber);
        List<PendingVacationApprovalDTO> pendingApprovals = getPendingApprovals(approverId);

        return VacationManagementResponseDTO.of(
                summary,
                myVacationHistories,
                pendingApprovals
        );
    }

    private void validateEmployeeNumber(String employeeNumber) {
        if (employeeNumber == null || employeeNumber.isBlank()) {
            throw new IllegalArgumentException("직원 사번은 필수입니다.");
        }
    }

    private void validateApproverId(Long approverId) {
        if (approverId == null) {
            throw new IllegalArgumentException("승인자 ID는 필수입니다.");
        }

        if (approverId <= 0) {
            throw new IllegalArgumentException("승인자 ID는 1 이상이어야 합니다.");
        }
    }
}