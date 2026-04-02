package kr.co.cloudStudy.employee.dto;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.cloudStudy.attendance.dto.AttendanceHistoryResponseDTO;
import kr.co.cloudStudy.attendance.enums.AttendanceStatus;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.vacation.dto.PendingVacationApprovalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(description = "직원정보 응답상세 dto")
public class EmployeeDetailResDto {
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
	
	
	// 부서 외래키
	@Schema(description = "부서PK" , example = "1")
	private Long departmentId; // 부서 ID (PK)
	
	
	// !!근태관련 필드
	@Schema(description = "근무 날짜", example = "2024.05.24 (금)" )
	private LocalDate workDate;
	@Schema(description = "출근", example = "08:52")
	private LocalDateTime checkInTime;
	@Schema(description = "퇴근", example = "18:05")
	private LocalDateTime checkOutTime;
	@Schema(description = "상태", example = "정상")
	private AttendanceStatus attendanceStatus;
	
	@Schema(description = "근태관련 목록", example = "근태 기록")
	public List<AttendanceHistoryResponseDTO> attendanceHistory;
	// 근태관련 필드 (끝)
	
	
	//휴가관련 필드 
	@Schema(description = "휴가 유형", example = "연차")
    private String vacationType;
	
	// 기간은 리액트에서 조합
	@Schema(description = "휴가 시작일", example = "2026-04-01")
    private LocalDate startDate;
	@Schema(description = "휴가 종료일", example = "2026-04-03")
    private LocalDate endDate;
	@Schema(description = "휴가 일수", example = "2")
    private BigDecimal vacationDays;
	@Schema(description = "휴가 상태", example = "APPROVED")
    private String vacationStatus;
	
	@Schema(description = "휴가관련 리스트" ,example = "최근 휴가 신청 내역")
	public List<PendingVacationApprovalDTO> pendingVacation;
	
	
	
	//휴가관련 필드 (끝)
	// 조회요청 응답메서드 : fromEntity 
	//	- 엔티티에 있는 데이터를 화면에 띄우기 위함.
	public static EmployeeDetailResDto fromEntity(Employee entity) {
		return EmployeeDetailResDto.builder()
				.employeeId(entity.getEmployeeId())								// 직원 Id
				.name(entity.getName())	// 이름
				.employeeNumber(entity.getEmployeeNumber()) 					// 사번
				.position(entity.getPosition())	// 직책
				.email(entity.getEmail())										//  이메일
				.departmentName(entity.getDepartment() != null ?				//	**부서명 지정
						// 나중에 getDepartmentName으로 수정
						// 아래꺼 연동을 위해 임시로 deptName으로 했음.
                        entity.getDepartment().getDeptName() : "소속없음")		//	**
				
				.status(entity.getStatus())										// 상태 : 활성,비활성,퇴사
				
				// 데이터가  엔티티쪽에 저장된 시점에 실행하는거니깐...
				// 엔티티에서 휴가관련 리스트 가져와서 -> 
				//응답 객체로 바꾸기	(.attendanceHistory) , 없으면 빈배열을 반환한다.
				.attendanceHistory(entity.getAttendances() != null ?
						// 엔티티에 저장된 근태관련 값이 null이 아님을 확인 후 ->
						entity.getAttendances().stream().map().collect(Collectors.toList());
						);
						
						
				
				.build()														
	}
	
	
}

