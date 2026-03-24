package kr.co.cloudStudy.department.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//부서 등록 요청용 DTO
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqDeptDTO {

		private Long id;                     // 부서 PK
		private String deptCode;			 // 부서 코드
		private String deptName;			 // 부서 이름
		private String description;			 // 부서 설명
			
}
