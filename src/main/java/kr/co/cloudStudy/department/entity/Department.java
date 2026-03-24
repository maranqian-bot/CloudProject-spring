package kr.co.cloudStudy.department.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor  // JPA를 위한 기본 생성자
@AllArgsConstructor  // 빌더를 사용하기 위한 전체 생성자
@Table(name = "department") // DB 테이블명과 매핑
@Builder // 빌더 패턴 적용
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 부서 ID (PK)
	
	@Column(name = "dept_code")
	private String deptCode; // 부서 코드
	
	@Column(name = "dept_name")
	private String deptName; // 부서 이름
	
	private Long managerId; // 부서 관리자 ID
	private String description; // 부서 설명
	
	@Column(name = "created_at")
	private LocalDateTime createdAt; // 부서 생성일
	
	@Column(name = "updated_at")
	private LocalDateTime updatedAt; // 부서 수정일
	
	// 데이터 저장 전 생성일 자동 실행
	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	// 데이터 수정 전 수정일 자동 실행
	@PrePersist
	protected void onUpdate() {
		this.createdAt = LocalDateTime.now();
	}

}
