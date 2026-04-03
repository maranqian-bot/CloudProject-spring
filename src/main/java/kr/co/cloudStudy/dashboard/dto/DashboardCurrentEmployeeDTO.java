package kr.co.cloudStudy.dashboard.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "대시보드 현재 로그인 사용자 정보 DTO")
public class DashboardCurrentEmployeeDTO {

    @Schema(description = "직원 ID", example = "3")
    private Long employeeId;

    @Schema(description = "직원 이름", example = "홍길동")
    private String employeeName;

    @Schema(description = "사용 가능 연차", example = "12.5")
    private BigDecimal availableVacationDays;

    public static DashboardCurrentEmployeeDTO of(Employee employee, BigDecimal availableVacationDays) {
        return DashboardCurrentEmployeeDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getName())
                .availableVacationDays(availableVacationDays)
                .build();
    }
}