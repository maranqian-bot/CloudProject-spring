package kr.co.cloudStudy.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "직원 검색 조건 DTO")
public class EmployeeSearchDto {
	@Schema(description = "통합 검색어 (이름 또는 사번)", example = "홍길동")
	private String keyword;	// 이름이나 사번 통합검색창입력값
	@Schema(description = "부서명 검색 조건", example = "개발팀")
	private String department;	// 부서 선택값
	@Schema(description = "재직 상태 조건 (1: 재직, 0: 퇴사)", example = "1")
	private String status;		// 상태 선택값
	@Builder.Default
	@Schema(description = "요청할 페이지 번호 (0부터 시작)", example = "0")
	private int page = 0;		// 요청할 페이지 번호
	@Builder.Default
	@Schema(description = "한 페이지당 보여줄 직원 수", example = "10")
	private int size = 10;		// 한번에 보여줄 직원수
}
 