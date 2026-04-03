package kr.co.cloudStudy.vacation.service.impl;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import kr.co.cloudStudy.vacation.dto.VacationDecisionResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationManagementSummaryDTO;
import kr.co.cloudStudy.vacation.dto.VacationRejectRequestDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestEmployeeResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListItemDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListResponseDTO;
import kr.co.cloudStudy.vacation.dto.VacationRequestListSummaryDTO;
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
    public List<PendingVacationApprovalDTO> getPendingApprovals(String approverEmployeeNumber) {
        validateEmployeeNumber(approverEmployeeNumber);

        List<Vacation> vacationList =
                vacationRepository.findPendingApprovalsWithEmployee(
                        approverEmployeeNumber,
                        VacationStatus.PENDING
                );

        return vacationList.stream()
                .map(PendingVacationApprovalDTO::from)
                .toList();
    }

    @Override
    public VacationManagementResponseDTO getVacationManagementPage(
            String employeeNumber,
            String approverEmployeeNumber,
            Integer year
    ) {
        VacationManagementSummaryDTO summary =
                annualLeaveBalanceService.getVacationManagementSummary(employeeNumber, year);

        List<MyVacationHistoryDTO> myVacationHistories = getMyVacationHistory(employeeNumber);
        List<PendingVacationApprovalDTO> pendingApprovals = getPendingApprovals(approverEmployeeNumber);

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

        BigDecimal requestedDays = resolveRequestedDays(request);

        if (requestedDays.compareTo(annualLeaveBalance.getRemainingDays()) > 0) {
            throw new IllegalArgumentException("잔여 연차를 초과하여 신청할 수 없습니다.");
        }

        Vacation savedVacation = vacationRepository.save(
                Vacation.create(
                        employee,
                        request.getVacationType(),
                        request.getStartDate(),
                        requestedDays,
                        buildVacationReason(request)
                )
        );

        return VacationCreateResponseDTO.from(savedVacation);
    }

    @Override
    public VacationRequestListResponseDTO getVacationRequestList(
            String approverEmployeeNumber,
            Integer page,
            Integer size,
            String type
    ) {
        validateEmployeeNumber(approverEmployeeNumber);
        validatePage(page);
        validateSize(size);

        VacationType vacationType = normalizeVacationType(type);

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Vacation> vacationPage =
                vacationRepository.findVacationRequestList(approverEmployeeNumber, vacationType, pageRequest);

        VacationRequestListSummaryDTO summary = getVacationRequestListSummary(approverEmployeeNumber);

        // Page 의존은 Service에서만 처리
        List<VacationRequestListItemDTO> list = vacationPage.getContent().stream()
                .map(VacationRequestListItemDTO::from)
                .toList();

        return VacationRequestListResponseDTO.of(
                summary,
                list,
                vacationPage.getNumber() + 1,
                vacationPage.getTotalPages(),
                vacationPage.getTotalElements(),
                vacationPage.getSize()
        );
    }

    @Override
    public VacationRequestListSummaryDTO getVacationRequestListSummary(String approverEmployeeNumber) {
        validateEmployeeNumber(approverEmployeeNumber);

        long pendingCount = vacationRepository
                .countByApprover_EmployeeNumberAndVacationStatus(approverEmployeeNumber, VacationStatus.PENDING);
        long approvedCount = vacationRepository
                .countByApprover_EmployeeNumberAndVacationStatus(approverEmployeeNumber, VacationStatus.APPROVED);
        long rejectedCount = vacationRepository
                .countByApprover_EmployeeNumberAndVacationStatus(approverEmployeeNumber, VacationStatus.REJECTED);

        YearMonth currentMonth = YearMonth.now();
        long monthlyVacationCount = vacationRepository.countDistinctEmployeesByApproverAndStartDateBetween(
                approverEmployeeNumber,
                currentMonth.atDay(1),
                currentMonth.atEndOfMonth()
        );

        return VacationRequestListSummaryDTO.of(
                pendingCount,
                approvedCount,
                rejectedCount,
                monthlyVacationCount
        );
    }

    @Override
    @Transactional
    public VacationDecisionResponseDTO approveVacationRequest(Long vacationId, String approverEmployeeNumber) {
        validateVacationId(vacationId);
        validateEmployeeNumber(approverEmployeeNumber);

        Vacation vacation = vacationRepository.findDetailByVacationId(vacationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 휴가 신청을 찾을 수 없습니다."));

        Employee approver = validateVacationApproverAuthority(vacation, approverEmployeeNumber);

        if (vacation.getVacationStatus() != VacationStatus.PENDING) {
            throw new IllegalArgumentException("대기 상태인 휴가만 승인할 수 있습니다.");
        }

        // 이미 조회된 approver 객체 사용
        vacation.approve(approver);

        return VacationDecisionResponseDTO.from(vacation);
    }

    @Override
    @Transactional
    public VacationDecisionResponseDTO rejectVacationRequest(
            Long vacationId,
            VacationRejectRequestDTO request,
            String approverEmployeeNumber
    ) {
        validateVacationId(vacationId);
        validateRejectRequest(request);
        validateEmployeeNumber(approverEmployeeNumber);

        Vacation vacation = vacationRepository.findDetailByVacationId(vacationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 휴가 신청을 찾을 수 없습니다."));

        Employee approver = validateVacationApproverAuthority(vacation, approverEmployeeNumber);

        if (vacation.getVacationStatus() != VacationStatus.PENDING) {
            throw new IllegalArgumentException("대기 상태인 휴가만 반려할 수 있습니다.");
        }

        // 이미 조회된 approver 객체 사용
        vacation.reject(approver, request.getRejectReason().trim());

        return VacationDecisionResponseDTO.from(vacation);
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

        if ((request.getVacationType() == VacationType.HALF_AM
                || request.getVacationType() == VacationType.HALF_PM)
                && request.getDays().compareTo(new BigDecimal("0.5")) != 0) {
            throw new IllegalArgumentException("반차는 사용 일수가 0.5일이어야 합니다.");
        }

        if (request.getVacationType() == VacationType.ETC
                && (request.getReasonDetail() == null || request.getReasonDetail().isBlank())) {
            throw new IllegalArgumentException("기타 사유를 입력해 주세요.");
        }
    }

    private void validatePage(Integer page) {
        if (page == null || page <= 0) {
            throw new IllegalArgumentException("페이지 번호는 1 이상이어야 합니다.");
        }
    }

    private void validateSize(Integer size) {
        if (size == null || size <= 0) {
            throw new IllegalArgumentException("페이지 크기는 1 이상이어야 합니다.");
        }
    }

    private void validateVacationId(Long vacationId) {
        if (vacationId == null || vacationId <= 0) {
            throw new IllegalArgumentException("휴가 신청 ID는 1 이상이어야 합니다.");
        }
    }

    private void validateRejectRequest(VacationRejectRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("휴가 반려 요청은 필수입니다.");
        }

        if (request.getRejectReason() == null || request.getRejectReason().isBlank()) {
            throw new IllegalArgumentException("반려 사유를 입력해 주세요.");
        }
    }

    private Employee validateVacationApproverAuthority(Vacation vacation, String approverEmployeeNumber) {
        // 로그인한 승인자 기준 권한 검증
        if (vacation.getApprover() == null
                || !vacation.getApprover().getEmployeeNumber().equals(approverEmployeeNumber)) {
            throw new IllegalArgumentException("해당 휴가를 처리할 권한이 없습니다.");
        }

        return vacation.getApprover();
    }

    private VacationType normalizeVacationType(String type) {
        if (type == null || type.isBlank() || "ALL".equalsIgnoreCase(type)) {
            return null;
        }

        try {
            return VacationType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("올바르지 않은 휴가 유형입니다.");
        }
    }
    
    private BigDecimal resolveRequestedDays(VacationCreateRequestDTO request) {
        if (request.getVacationType() == VacationType.HALF_AM
                || request.getVacationType() == VacationType.HALF_PM) {
            return new BigDecimal("0.5");
        }

        return request.getDays();
    }

    private String buildVacationReason(VacationCreateRequestDTO request) {
        // 기타 휴가는 상세 사유 사용
        if (request.getVacationType() == VacationType.ETC) {
            return request.getReasonDetail().trim();
        }

        return request.getVacationType().name() + " 신청";
    }
}