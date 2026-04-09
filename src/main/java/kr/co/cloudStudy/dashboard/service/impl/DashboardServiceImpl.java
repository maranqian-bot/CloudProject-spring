package kr.co.cloudStudy.dashboard.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.annualLeaveBalance.entity.AnnualLeaveBalance;
import kr.co.cloudStudy.annualLeaveBalance.repository.AnnualLeaveBalanceRepository;
import kr.co.cloudStudy.attendance.entity.Attendance;
import kr.co.cloudStudy.attendance.enums.AttendanceStatus;
import kr.co.cloudStudy.attendance.repository.AttendanceRepository;
import kr.co.cloudStudy.dashboard.dto.DashboardCurrentEmployeeDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardRecentActivityItemDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardRecentActivityResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardSummaryDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardTodayAttendanceResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardVacationRequestDTO;
import kr.co.cloudStudy.dashboard.service.DashboardService;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import kr.co.cloudStudy.vacation.entity.Vacation;
import kr.co.cloudStudy.vacation.entity.VacationStatus;
import kr.co.cloudStudy.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {
	
	

    private final EmployeeRepository employeeRepository;
    private final AnnualLeaveBalanceRepository annualLeaveBalanceRepository;
    private final AttendanceRepository attendanceRepository;
    private final VacationRepository vacationRepository;

    @Override
    public DashboardResponseDTO getDashboard(String loginIdentifier) {
//    	System.out.println("1. dashboard start");
        validateEmployeeNumber(loginIdentifier);
        Employee employee = employeeRepository.findByEmployeeNumber(loginIdentifier)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원 정보를 찾을 수 없습니다."));
//        System.out.println("2. employee found: " + employee.getEmployeeNumber());

        String employeeNumber = employee.getEmployeeNumber();
//        System.out.println("3. annual leave start");
        

        BigDecimal availableVacationDays = annualLeaveBalanceRepository
                .findByEmployee_EmployeeNumberAndYear(employeeNumber, LocalDate.now().getYear())
                .map(AnnualLeaveBalance::getRemainingDays)
                .orElse(BigDecimal.ZERO);
        
//        System.out.println("4. absent count start");
        long absentCount = getAbsentTeamMemberCount(employee);
        
//        System.out.println("5. work days start");
        long workDays = getCurrentMonthWorkDays(employee.getEmployeeId());

        DashboardSummaryDTO summary = DashboardSummaryDTO.of(absentCount, workDays);
        DashboardCurrentEmployeeDTO currentEmployee =
                DashboardCurrentEmployeeDTO.of(employee, availableVacationDays);
//        System.out.println("6. vacation requests start");
        List<DashboardVacationRequestDTO> vacationRequests = getDashboardVacationRequests(employeeNumber);

        return DashboardResponseDTO.of(summary, currentEmployee, vacationRequests);
    }
    
    
    @Override
    public DashboardTodayAttendanceResponseDTO getTodayAttendance(String employeeNumber) {
        validateEmployeeNumber(employeeNumber);

        Employee employee = getEmployeeWithDepartment(employeeNumber);
        LocalDate today = LocalDate.now();

        Optional<Attendance> attendanceOptional = attendanceRepository
                .findByEmployee_EmployeeIdAndWorkDate(employee.getEmployeeId(), today);

        return DashboardTodayAttendanceResponseDTO.of(today, attendanceOptional);
    }

    @Override
    public DashboardRecentActivityResponseDTO getRecentActivities(String employeeNumber, Integer page, Integer size) {
        validateEmployeeNumber(employeeNumber);
        validatePage(page);
        validateSize(size);

        Employee employee = getEmployeeWithDepartment(employeeNumber);

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Attendance> attendancePage =
                attendanceRepository.findByEmployee_EmployeeIdOrderByWorkDateDesc(
                        employee.getEmployeeId(),
                        pageRequest
                );

        List<DashboardRecentActivityItemDTO> items = attendancePage.getContent().stream()
                .map(DashboardRecentActivityItemDTO::from)
                .toList();

        return DashboardRecentActivityResponseDTO.of(
                items,
                attendancePage.getTotalElements(),
                attendancePage.getNumber() + 1,
                attendancePage.getTotalPages(),
                attendancePage.getSize()
        );
    }

    @Override
    @Transactional
    public DashboardTodayAttendanceResponseDTO checkIn(String employeeNumber) {
        validateEmployeeNumber(employeeNumber);

        Employee employee = getEmployeeWithDepartment(employeeNumber);
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        Optional<Attendance> attendanceOptional = attendanceRepository
                .findByEmployee_EmployeeIdAndWorkDate(employee.getEmployeeId(), today);

        if (attendanceOptional.isEmpty()) {
            Attendance savedAttendance = attendanceRepository.save(
                    Attendance.createCheckIn(employee, now, AttendanceStatus.NORMAL)
            );
            return DashboardTodayAttendanceResponseDTO.of(today, Optional.of(savedAttendance));
        }

        Attendance attendance = attendanceOptional.get();

        if (attendance.getAttendanceStatus() == AttendanceStatus.VACATION
                || attendance.getAttendanceStatus() == AttendanceStatus.ABSENT) {
            throw new IllegalArgumentException("이미 부재 처리된 근태 기록은 출근 처리할 수 없습니다.");
        }

        if (attendance.getCheckInTime() != null) {
            throw new IllegalArgumentException("이미 출근 처리되었습니다.");
        }

        // 기존 기록이 있으면 출근 정보만 갱신
        attendance.markCheckIn(now, AttendanceStatus.NORMAL);

        return DashboardTodayAttendanceResponseDTO.of(today, Optional.of(attendance));
    }

    @Override
    @Transactional
    public DashboardTodayAttendanceResponseDTO checkOut(String employeeNumber, Long attendanceId) {
        validateEmployeeNumber(employeeNumber);
        validateAttendanceId(attendanceId);

        Employee employee = getEmployeeWithDepartment(employeeNumber);
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        Attendance attendance = attendanceRepository
                .findByAttendanceIdAndEmployee_EmployeeId(attendanceId, employee.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 근태 기록을 찾을 수 없습니다."));

        if (!today.equals(attendance.getWorkDate())) {
            throw new IllegalArgumentException("오늘 근태 기록만 퇴근 처리할 수 있습니다.");
        }

        if (attendance.getCheckInTime() == null) {
            throw new IllegalArgumentException("출근 처리되지 않은 기록은 퇴근 처리할 수 없습니다.");
        }

        if (attendance.getCheckOutTime() != null) {
            throw new IllegalArgumentException("이미 퇴근 처리되었습니다.");
        }

        int workMinutes = Math.max(
                0,
                (int) java.time.Duration.between(attendance.getCheckInTime(), now).toMinutes()
        );

        AttendanceStatus attendanceStatus = determineCheckOutStatus(workMinutes);

        attendance.checkOut(now, workMinutes, attendanceStatus);

        return DashboardTodayAttendanceResponseDTO.of(today, Optional.of(attendance));
    }

    private Employee getEmployeeWithDepartment(String employeeNumber) {
        return employeeRepository.findWithDepartmentByEmployeeNumber(employeeNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 직원 정보를 찾을 수 없습니다."));
    }

    private long getAbsentTeamMemberCount(Employee employee) {
        if (employee.getDepartment() == null) {
            return 0L;
        }

        return attendanceRepository.countDistinctByDepartmentAndWorkDateAndStatusInExcludingEmployee(
                employee.getDepartment().getDepartmentId(),
                LocalDate.now(),
                employee.getEmployeeId(),
                List.of(AttendanceStatus.ABSENT, AttendanceStatus.VACATION)
        );
    }

    private long getCurrentMonthWorkDays(Long employeeId) {
        YearMonth currentMonth = YearMonth.now();

        return attendanceRepository.countByEmployee_EmployeeIdAndWorkDateBetweenAndAttendanceStatusIn(
                employeeId,
                currentMonth.atDay(1),
                currentMonth.atEndOfMonth(),
                List.of(
                        AttendanceStatus.NORMAL,
                        AttendanceStatus.LATE,
                        AttendanceStatus.EARLY_LEAVE,
                        AttendanceStatus.OVER_TIME
                )
        );
    }

    private List<DashboardVacationRequestDTO> getDashboardVacationRequests(String employeeNumber) {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate endOfYear = LocalDate.now().withMonth(12).withDayOfMonth(31);

        List<Vacation> pendingApprovals =
                vacationRepository.findPendingApprovalsWithEmployee(
                        employeeNumber,
                        VacationStatus.PENDING
                );

        List<Vacation> approvedVacations =
                vacationRepository.findApprovedDashboardVacationsWithEmployee(
                        employeeNumber,
                        VacationStatus.APPROVED,
                        startOfYear,
                        endOfYear
                );

        Map<Long, DashboardVacationRequestDTO> merged = new LinkedHashMap<>();

        pendingApprovals.forEach(vacation -> merged.put(
                vacation.getVacationId(),
                DashboardVacationRequestDTO.from(vacation, vacation.getEmployee().getEmployeeId())
        ));

        approvedVacations.forEach(vacation -> merged.putIfAbsent(
                vacation.getVacationId(),
                DashboardVacationRequestDTO.from(vacation, vacation.getEmployee().getEmployeeId())
        ));

        return merged.values().stream().toList();
    }

    private AttendanceStatus determineCheckOutStatus(int workMinutes) {
        if (workMinutes >= 600) {
            return AttendanceStatus.OVER_TIME;
        }

        if (workMinutes < 480) {
            return AttendanceStatus.EARLY_LEAVE;
        }

        return AttendanceStatus.NORMAL;
    }

    private void validateEmployeeNumber(String employeeNumber) {
        if (employeeNumber == null || employeeNumber.isBlank()) {
            throw new IllegalArgumentException("직원 사번은 필수입니다.");
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

    private void validateAttendanceId(Long attendanceId) {
        if (attendanceId == null || attendanceId <= 0) {
            throw new IllegalArgumentException("근태 기록 ID는 1 이상이어야 합니다.");
        }
    }
    
}