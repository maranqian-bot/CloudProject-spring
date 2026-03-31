package kr.co.cloudStudy.employee.dto;



import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(description = "직원정보 응답상세 dto")
public class EmployeeResDto {
	@Schema(description = "직원Id(PK)", example = "1")
	private Long employeeId;							// 기본키
	@Schema(description = "직원번호", example = "20240001")
    private String employeeNumber; 						// 직원번호
	@Schema(description = "이름", example = "홍길동")
	private String name; 								// 이름
	@Schema(description = "소속부서명", example = "인사팀")
	private String departmentName;						// 부서명
	@Schema(description = "직책", example = "시니어 매니저")
	private String position;							// 직책
	@Schema(description = "이메일주소", example = "hong@cloudstudy.co.kr")
	private String email;								// 이메일
	@Schema(description = "재직상태 (ACTIVE/INACTIVE)", example = "ACTIVE")
	private String status;								// 상태

	// 조회요청 응답메서드 : fromEntity
	//	- 엔티티에 있는 데이터를 화면에 띄우기 위함.
	public static EmployeeResDto fromEntity(Employee entity) {
		return EmployeeResDto.builder()
				.employeeId(entity.getEmployeeId())
				.name(entity.getName())
				.employeeNumber(entity.getEmployeeNumber())
				.position(entity.getPosition())
				.email(entity.getEmail())
				.departmentName(entity.getDepartment() != null ?
						// 나중에 getDepartmentName으로 수정
						// 아래꺼 연동을 위해 임시로 deptName으로 했음.
                        entity.getDepartment().getDeptName() : "소속없음")
				.status(entity.getStatus())
				.build();
	}
	
}

