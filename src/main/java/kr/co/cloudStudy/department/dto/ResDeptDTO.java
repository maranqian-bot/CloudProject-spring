package kr.co.cloudStudy.department.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//부서 조회용 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResDeptDTO {
	
	private Long id;                     // 부서 PK
	private String deptCode;			 // 부서 코드
	private String deptName;			 // 부서 이름
	private String description;			 // 부서 설명
	private Long managerId;				 // 관리자 ID
	private LocalDateTime createdAt;     // 생성일
	
}
