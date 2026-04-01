package kr.co.cloudStudy.annualLeaveBalance.entity;

import jakarta.persistence.*;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "annual_leave_balance",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_leave_balance_employee_year",
            columnNames = {"employee_number", "year"}
        )
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AnnualLeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_balance_id")
    private Long leaveBalanceId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "granted_days", nullable = false, precision = 4, scale = 1)
    private BigDecimal grantedDays;

    @Column(name = "used_days", nullable = false, precision = 4, scale = 1)
    private BigDecimal usedDays;

    @Column(name = "remaining_days", nullable = false, precision = 4, scale = 1)
    private BigDecimal remainingDays;

    @Column(name = "tenure_years")
    private Integer tenureYears;

    @Column(name = "grant_reason", length = 250)
    private String grantReason;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "employee_number",
        referencedColumnName = "employee_number",
        nullable = false
    )
    private Employee employee;

    public void updateLeaveUsage(BigDecimal usedDays, BigDecimal remainingDays) {
        this.usedDays = usedDays;
        this.remainingDays = remainingDays;
    }
}