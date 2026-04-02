package kr.co.cloudStudy.vacation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.annualLeaveBalance.entity.AnnualLeaveBalance;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "휴가 신청 페이지 대상자 정보 응답 DTO")
public class VacationRequestEmployeeResponseDTO {

    @Schema(description = "직원 ID", example = "1")
    private Long employeeId;

    @Schema(description = "직원 사번", example = "EMP-2024-002")
    private String employeeNumber;

    @Schema(description = "직원 이름", example = "홍길동")
    private String employeeName;

    @Schema(description = "부서명", example = "인사부")
    private String departmentName;

    @Schema(description = "직책", example = "사원")
    private String position;

    @Schema(description = "입사일", example = "2024-03-01")
    private LocalDate hireDate;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.png")
    private String profileImage;

    @Schema(description = "사용 가능 연차", example = "12.5")
    private BigDecimal availableVacationDays;

    public static VacationRequestEmployeeResponseDTO of(
            Employee employee,
            AnnualLeaveBalance annualLeaveBalance
    ) {
        return VacationRequestEmployeeResponseDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeNumber(employee.getEmployeeNumber())
                .employeeName(employee.getName())
                .departmentName(employee.getDepartment().getDeptName())
                .position(employee.getPosition())
                .hireDate(employee.getHireDate())
                .profileImage(employee.getProfileImg())
                .availableVacationDays(annualLeaveBalance.getRemainingDays())
                .build();
    }
}