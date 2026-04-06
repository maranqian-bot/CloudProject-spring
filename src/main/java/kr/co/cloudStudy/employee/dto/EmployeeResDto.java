package kr.co.cloudStudy.employee.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(description = "직원정보 응답상세 dto")
public class EmployeeResDto {
    @Schema(description = "직원Id(PK)", example = "1")
    private Long employeeId;
    @Schema(description = "직원번호", example = "20240001")
    private String employeeNumber;
    @Schema(description = "이름", example = "홍길동")
    private String name;
    @Schema(description = "소속부서명", example = "인사팀")
    private String departmentName;
    @Schema(description = "직책", example = "시니어 매니저")
    private String position;
    @Schema(description = "이메일주소", example = "hong@cloudstudy.co.kr")
    private String email;
    @Schema(description = "재직상태 (ACTIVE/INACTIVE)", example = "ACTIVE")
    private String status;
    @Schema(description = "부서PK", example = "1")
    private Long departmentId;

    // 💡 내부 전용 클래스로 리스트 타입 변경 (String 타입 사ㄴ용)
    @Schema(description = "근태관련 목록")
    public List<AttendanceDetail> attendanceHistory;

    @Schema(description = "휴가관리 리스트")
    public List<VacationDetail> pendingVacation;

    // 💡 근태 상세 정보 전용 클래스 (Enum 대신 String 사용)
    @Builder @Getter @AllArgsConstructor @NoArgsConstructor
    public static class AttendanceDetail {
        private LocalDate workDate;
        private LocalDateTime checkInTime;
        private LocalDateTime checkOutTime;
        private String attendanceStatus; 
    }

    // 💡 휴가 상세 정보 전용 클래스 (Enum 대신 String 사용)
    @Builder @Getter @AllArgsConstructor @NoArgsConstructor
    public static class VacationDetail {
        private String vacationType;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal vacationDays;
        private String vacationStatus; 
    }

    public static EmployeeResDto fromEntity(Employee employee) {
        return EmployeeResDto.builder()
                .employeeId(employee.getEmployeeId())
                .name(employee.getName())
                .employeeNumber(employee.getEmployeeNumber())
                .position(employee.getPosition())
                .email(employee.getEmail())
                .departmentName(employee.getDepartment() != null ? 
                                employee.getDepartment().getDeptName() : "소속없음")
                .status(employee.getStatus())
                // ✅ 리스트는 여기서 채우지 않고, 서비스에서 채울 수 있도록 빈 리스트로 초기화만 함
                .attendanceHistory(new ArrayList<>())
                .pendingVacation(new ArrayList<>()) 
                .build();
    }
}