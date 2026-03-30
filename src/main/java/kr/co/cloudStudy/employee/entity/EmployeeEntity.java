package kr.co.cloudStudy.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
import jakarta.persistence.Table;
import kr.co.cloudStudy.department.entity.Department;
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
public class EmployeeEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private Long empId;	// 기본키
	
	@Column(name = "employee_number",nullable = false , unique = true, length = 20)	// 직원사번	
	private String employeeNumber;
	
	@Column(name = "name" , nullable = false , length = 50)
	private String name; // 이름
	
	@Column(name = "position", length = 50)
	private String position; // 직책
	
	@Column(name = "email", length = 100)
	private String email;	// 이메일
	
	@Column(name = "status", length = 20)
	@Builder.Default
	private String status = "활성";  // 상태
	
	@Column(name = "password", nullable = false, length = 255 )	// 암호화 대비해서 길이 넉넉히...
	private String password;	//비번
	
	@Column(name = "hire_date")
	private LocalDate hireDate; // 입사일	
	
	@Column(name = "role", length = 20)	
	@Builder.Default
	private String role = "EMPLOYEE"; // 시스템 역할
	
	@CreatedDate
	@Column(name = "created_at" , updatable = false,
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")			
	private LocalDateTime createdAt;	// 생성일
	
	@LastModifiedDate
	@Column(name = "updated_at",
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedAt;	// 수정일		

	  @ManyToOne(fetch = FetchType.LAZY) 
	   @JoinColumn(name = "dept_id", nullable = false) 
	   private Department department;   // 부서 외래키
	  
	  
	} 

