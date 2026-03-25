package kr.co.cloudStudy.attendance.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "attendance")
public class Attendance {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "attendance_id", nullable = false, updatable = false)
   private Long attendanceId;
   
//   @ManyToOne
//   @JoinColumn(name = "emp_id", nullable = false)
   // employee 엔티티와 조인할 예정
   private Long employeeId; 
   
   @Column(name = "work_date", nullable = false)
   private LocalDate workDate;
   
   @Column(name = "check_in_time")
   private LocalDateTime checkInTime;
   
   @Column(name = "check_out_time")
   private LocalDateTime checkOutTime;
   
   @Column(name = "work_minutes")
   private Integer workMinutes; 
   
   @Enumerated(EnumType.STRING)
   @Column(name = "attendance_status", length = 20, nullable = false)
   private AttendanceStatus attendanceStatus;
   
   @Column(name = "created_at", nullable = false, updatable = false)
   private LocalDateTime createdAt;
   
   @Column(name = "updated_at", nullable = false)
   private LocalDateTime updatedAt;
   
   @PrePersist // DB에 처음 저장 직전에 실행
   public void prePersist() {
      this.createdAt = LocalDateTime.now();
      this.updatedAt = LocalDateTime.now();
   }
      
   @PreUpdate // 수정 직전에 실행
   public void preUpdate() {
      this.updatedAt = LocalDateTime.now();
   }
}
