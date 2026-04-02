package kr.co.cloudStudy.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kr.co.cloudStudy.department.entity.Department;
import kr.co.cloudStudy.employee.dto.EmployeeReqDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name ="employee")
public class Employee {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long employeeId;	// 직원Id (기본키)
	
	@Column(name = "employee_number",nullable = false , unique = true, length = 50)	// 직원사번	
	private String employeeNumber;
	
	@Column(name = "name" , nullable = false , length = 50)
	private String name; // 이름
	
	@Column(name = "position", nullable = false, length = 50)
	private String position; // 직책
	
	@Column(name = "email", nullable = false , unique = true, length = 100)
	private String email;	// 이메일
	
	// 1. 리액트의 "ACTIVE"와 값을 맞춤 (한글 "활성" 제거)
    @Column(name = "status", length = 50)
    @Builder.Default
    private String status = "ACTIVE"; 
	
	@Column(name = "password", nullable = false, length = 255 )	// 암호화 대비해서 길이 넉넉히...
	private String password;	//비번
	
	@Column(name = "hire_date")
	private LocalDate hireDate; // 입사일	
	
	@Column(name = "retire_date")
	private LocalDate retireDate; // 퇴사일
	
	
    @Column(name = "role", length = 50)		
    @Builder.Default	
    private String role = "EMPLOYEE";	// 시스템 역할
	
    @Column(name = "profile_img", length = 255)
    private String profileImg;			// 프로필 이미지
    
	@CreatedDate
	@Column(name = "created_at" , updatable = false,
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")			
	private LocalDateTime createdAt;	// 생성일
	
	@LastModifiedDate
	@Column(name = "updated_at",
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedAt;	// 수정일		

	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "department_id", nullable = false) 
	private Department department;   // 부서 외래키
	
	// 직원정보 수정을 위한 메서드
	public void updateEmployee(EmployeeReqDto reqDto, Department department) {
			this.employeeNumber = reqDto.getEmployeeNumber();
			this.name = reqDto.getName();
			this.email = reqDto.getEmail();
			this.password = reqDto.getPassword();
			this.position= reqDto.getPosition();	
			this.department =department;			// 부서 기본키 받는 용도
			this.role = reqDto.getRole();			// 시스템역할				
			this.hireDate= reqDto.getHireDate();	// 입사일
			this.status= reqDto.getStatus();		// 상태 : 활성 / 비활성 / 퇴사
			// 상태관련 (퇴사일 부여될지 조건)
			this.retireDate = ("RESIGNED".equals(reqDto.getStatus()) || "퇴사".equals(reqDto.getStatus())) 
	                  ? reqDto.getRetireDate() : null;
	}
		
}
