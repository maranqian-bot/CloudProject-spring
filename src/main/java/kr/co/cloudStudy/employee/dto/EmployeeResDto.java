package kr.co.cloudStudy.employee.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "직원 정보 응답 DTO")
public class EmployeeResDto {
	@Schema(description = "직원 고유 번호", example = "1")
	private Long id;	// 직원번호
	@Schema(description = "직원 이름", example = "홍길동")
	private String name; 	// 이름
	@Schema(description = "부서 ID", example = "10")
	private Long deptId;	// 부서명
	@Schema(description = "직책", example = "사원")
	private String position;	// 직책
	@Schema(description = "이메일", example = "hong@example.com")
	private String email;	// 이메일
	@Schema(description = "상태 코드 (1: 재직, 0: 퇴사)", example = "1")
	private Integer status;	// 상태
	
	// dto로 변환하기. 메서드 만들어서 -> 객체로 디티오 반환
	public static EmployeeResDto fromEntity(EmployeeEntity entity) {
		return EmployeeResDto.builder()
				.id(entity.getId())
				.name(entity.getName())
				.deptId(entity.getDeptId())
				.position(entity.getPosition())
				.email(entity.getEmail())
				.status(entity.getStatus())
				.build();
	}
	
}