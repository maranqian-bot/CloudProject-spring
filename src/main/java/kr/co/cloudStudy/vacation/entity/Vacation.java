package kr.co.cloudStudy.vacation.entity;

import jakarta.persistence.*;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vacation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long vacationId;

    @Column(name = "vacation_type", nullable = false, length = 30)
    private String vacationType;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "vacation_days", nullable = false, precision = 4, scale = 1)
    private BigDecimal vacationDays;

    @Column(name = "vacation_reason", nullable = false, length = 255)
    private String vacationReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "vacation_status", nullable = false, length = 20)
    private VacationStatus vacationStatus;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "reject_reason", length = 255)
    private String rejectReason;

    @Column(name = "approver_name", length = 50)
    private String approverName;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private Employee approver;

    public void approve(Employee approver) {
        this.vacationStatus = VacationStatus.APPROVED;
        this.approver = approver;
        this.approverName = approver.getName();
        this.approvedAt = LocalDateTime.now();
        this.rejectReason = null;
        this.updatedAt = LocalDateTime.now();
    }

    public void reject(Employee approver, String rejectReason) {
        this.vacationStatus = VacationStatus.REJECTED;
        this.approver = approver;
        this.approverName = approver.getName();
        this.rejectReason = rejectReason;
        this.approvedAt = null;
        this.updatedAt = LocalDateTime.now();
    }
}