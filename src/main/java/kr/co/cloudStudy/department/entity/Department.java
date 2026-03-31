package kr.co.cloudStudy.department.entity;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import kr.co.cloudStudy.department.dto.ReqDeptDTO;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor  // JPA를 위한 기본 생성자
@AllArgsConstructor  // 빌더를 사용하기 위한 전체 생성자
@Table(name = "department") // DB 테이블명과 매핑
@EntityListeners(AuditingEntityListener.class)
@Builder // 빌더 패턴 적용
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id", nullable = false, updatable = false)
	private Long departmentId; // 부서 ID (PK)
	
	@Column(name = "dept_code", nullable = false, unique = true)
	private String deptCode; // 부서 코드
	
	@Column(name = "dept_name", nullable = false)
	@Setter
	private String deptName; // 부서 이름
	
	// Employee 엔터티 완성 후 연관 관계 매핑
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_number")
	private EmployeeEntity manager; // 부서 관리자 ID
	
	@Setter
	private String description; // 부서 설명
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt; // 부서 생성일
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt; // 부서 수정일
	
	// 데이터 저장 전 생성일 자동 실행
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
	
	// 데이터 수정 전 수정일 자동 실행
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	
	// 정적 팩토리 메서드(from) -> 엔터티 생성 책임을 서비스에서 도메인(Entity)으로 가져옴.
	public static Department from(ReqDeptDTO dto) {
        return Department.builder()
                .deptCode(dto.getDeptCode())
                .deptName(dto.getDeptName())
                .description(dto.getDescription())
                .build();
    }
	
	// 단순 ID 값이 아닌 직원 엔터티 객체를 직접 매핑하여 연관 관계 설정. (연관관계 편의 메서드)
	public void updateManager(EmployeeEntity manager) {
		this.manager = manager;   
	}
	
	// 비즈니스 메서드 -> 엔터티 스스로 상태 관리하도록 수정 (Setter 대신)
	public void update(ReqDeptDTO dto) {
        this.deptName = dto.getDeptName();
        this.description = dto.getDescription();
    }

}
