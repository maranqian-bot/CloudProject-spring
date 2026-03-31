package kr.co.cloudStudy.employee.dto;


import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class EmployeeResDto {
	
	private Long employeeId;	// 직원번호
	private String name; 	// 이름
	private String deptName;	// 부서명
	private String position;	// 직책
	private String email;	// 이메일
	private String status;	// 상태
	
	// dto로 변환하기. 메서드 만들어서 -> 객체로 디티오 반환
	public static EmployeeResDto fromEntity(EmployeeEntity entity) {
		return EmployeeResDto.builder()
				.employeeId(entity.getEmployeeId())
				.name(entity.getName())
				.deptName(entity.getDepartmentId() != null ? entity.getDepartmentId().getDeptName() : "소속 없음")
				.position(entity.getPosition())
				.email(entity.getEmail())
				.status(entity.getStatus())
				.build();
	}
	
}