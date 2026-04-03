package kr.co.cloudStudy.employee.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	public List<AttendanceHistoryResponseDTO> attendanceHistory;	// 위의 4개의 필드 이외의 값은 허용x
	// 근태관련 필드 (끝)
	
	
	//휴가관련 필드 
	@Schema(description = "휴가 유형", example = "연차")
    private String vacationType;
	
	// 기간은 리액트에서 조합
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "휴가 시작일", example = "2026-04-01")
    private LocalDate startDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Schema(description = "휴가 종료일", example = "2026-04-03")
    private LocalDate endDate;
	@Schema(description = "휴가 일수", example = "2")
    private BigDecimal vacationDays;
	@Schema(description = "휴가 상태", example = "APPROVED")
	private String vacationStatus;
	
	@Schema(description = "휴가관리 리스트" ,example = "최근 휴가 신청 내역")	
	public List<PendingVacationApprovalDTO> pendingVacation;	// 위의 5개의 필드만 리스트에 담겠다는 뜻
	
	
	
	//휴가관련 필드 (끝)	
	// 조회요청 응답메서드 : fromEntity 
	//	- 엔티티에 있는 데이터를 화면에 띄우기 위함.
	//	엔티티 -> 응답객체타입으로 변환 (응답객체의 필드를 사용)
	public static EmployeeResDto fromEntity(Employee employee) {
		return EmployeeResDto.builder()
				.employeeId(employee.getEmployeeId())								// 직원 Id
				.name(employee.getName())	// 이름
				.employeeNumber(employee.getEmployeeNumber()) 					// 사번
				.position(employee.getPosition())	// 직책
				.email(employee.getEmail())										//  이메일
				.departmentName(employee.getDepartment() != null ?				//	**부서명 지정
						// 나중에 getDepartmentName으로 수정
						// 아래꺼 연동을 위해 임시로 deptName으로 했음.
						employee.getDepartment().getDeptName() : "소속없음")		//	**
				
				.status(employee.getStatus())										// 상태 : 활성,비활성,퇴사
				 
				// .attendanceHistory() : 
				//		근무날짜,출근,퇴근,상태 필드만 응답으로 주겠다, getAttendances()로 모든 필드값을 가져옴 
				//		근태관련 데이터가 존재하는 경우에 응답dto의 필드값을 채워줌.
				.attendanceHistory(employee.getAttendance() != null ?
										employee.getAttendance().stream()
											.map(attendance -> AttendanceHistoryResponseDTO.builder()
													.workDate(attendance.getWorkDate())
													.checkInTime(attendance.getCheckInTime())
													.checkOutTime(attendance.getCheckOutTime())
													.attendanceStatus(attendance.getAttendanceStatus())
													.build())
											.collect(Collectors.toList()) : new ArrayList<>())
				
				.pendingVacation(employee.getVacation() != null ? 
	                    			employee.getVacation().stream()
	                    				.map(vacation -> PendingVacationApprovalDTO.builder() // 1. 공백 제거
	                    							.vacationType(vacation.getVacationType())
	                    							.startDate(vacation.getStartDate())
	                    							.endDate(vacation.getEndDate())
	                    							.vacationDays(vacation.getVacationDays())	
	                    							.vacationStatus(vacation.getVacationStatus().name()) // 2. () 추가
	                    							.build())
	                    				.collect(Collectors.toList()) : new ArrayList<>()) 
				.build();
	                    
	            
	}
}
	
 
	
	


