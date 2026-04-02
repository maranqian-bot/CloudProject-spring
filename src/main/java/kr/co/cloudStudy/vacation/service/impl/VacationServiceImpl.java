package kr.co.cloudStudy.vacation.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.annualLeaveBalance.entity.AnnualLeaveBalance;
import kr.co.cloudStudy.annualLeaveBalance.repository.AnnualLeaveBalanceRepository;
import kr.co.cloudStudy.annualLeaveBalance.service.AnnualLeaveBalanceService;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import kr.co.cloudStudy.vacation.dto.MyVacationHistoryDTO;
import kr.co.cloudStudy.vacation.dto.PendingVacationApprovalDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationCreateResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementSummaryDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestEmployeeResponseDTO;
import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;
import kr.co.cloudStudy.vacation.entity.VacationType;
import kr.co.cloudStudy.vacation.repository.VacationRepository;
import kr.co.cloudStudy.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;
    private final AnnualLeaveBalanceService annualLeaveBalanceService;
    private final AnnualLeaveBalanceRepository annualLeaveBalanceRepository;
    private final EmployeeRepository employeeRepository;

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

    @Override
    public VacationRequestEmployeeResponseDTO getVacationRequestEmployee(String employeeNumber, Integer year) {
        validateEmployeeNumber(employeeNumber);
        validateYear(year);

        Employee employee = employeeRepository.findWithDepartmentByEmployeeNumber(employeeNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원 정보를 찾을 수 없습니다."));

        // 연차 데이터 없음과 잔여 연차 0일 상태를 구분하기 위해 예외 처리
        AnnualLeaveBalance annualLeaveBalance = annualLeaveBalanceRepository
                .findByEmployee_EmployeeNumberAndYear(employeeNumber, year)
                .orElseThrow(() -> new IllegalArgumentException("해당 연도의 연차 정보가 존재하지 않습니다."));

        return VacationRequestEmployeeResponseDTO.of(employee, annualLeaveBalance);
    }

    @Override
    @Transactional
    public VacationCreateResponseDTO createVacationRequest(VacationCreateRequestDTO request) {
        validateCreateRequest(request);

        Employee employee = employeeRepository.findByEmployeeNumber(request.getEmployeeNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 직원 정보를 찾을 수 없습니다."));

        int targetYear = request.getStartDate().getYear();

        // 연차 데이터가 없는 상태는 정상적인 0일 상태와 다르게 처리
        AnnualLeaveBalance annualLeaveBalance = annualLeaveBalanceRepository
                .findByEmployee_EmployeeNumberAndYear(employee.getEmployeeNumber(), targetYear)
                .orElseThrow(() -> new IllegalArgumentException("해당 연도의 연차 정보가 존재하지 않습니다."));

        if (request.getDays().compareTo(annualLeaveBalance.getRemainingDays()) > 0) {
            throw new IllegalArgumentException("잔여 연차를 초과하여 신청할 수 없습니다.");
        }

        Vacation savedVacation = vacationRepository.save(
                Vacation.create(
                        employee,
                        request.getVacationType(),
                        request.getStartDate(),
                        request.getDays(),
                        buildVacationReason(request)
                )
        );

        return VacationCreateResponseDTO.from(savedVacation);
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

    private void validateYear(Integer year) {
        if (year == null) {
            throw new IllegalArgumentException("연도는 필수입니다.");
        }

        if (year < 2000) {
            throw new IllegalArgumentException("연도 값이 올바르지 않습니다.");
        }
    }

    private void validateCreateRequest(VacationCreateRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("휴가 신청 요청은 필수입니다.");
        }

        validateEmployeeNumber(request.getEmployeeNumber());

        if (request.getVacationType() == null) {
            throw new IllegalArgumentException("휴가 유형은 필수입니다.");
        }

        if (request.getStartDate() == null) {
            throw new IllegalArgumentException("휴가 시작일은 필수입니다.");
        }

        if (request.getDays() == null || request.getDays().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("사용 일수는 0보다 커야 합니다.");
        }

        if (request.getVacationType() == VacationType.ETC
                && (request.getReasonDetail() == null || request.getReasonDetail().isBlank())) {
            throw new IllegalArgumentException("기타 사유를 입력해 주세요.");
        }
    }

    private String buildVacationReason(VacationCreateRequestDTO request) {
        // 기타 휴가는 상세 사유 사용
        if (request.getVacationType() == VacationType.ETC) {
            return request.getReasonDetail().trim();
        }

        return request.getVacationType().name() + " 신청";
    }
}