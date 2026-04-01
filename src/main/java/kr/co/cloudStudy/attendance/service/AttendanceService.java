package kr.co.cloudStudy.attendance.service;

import java.io.OutputStream;

import kr.co.cloudStudy.attendance.dto.AttendanceHistoryPageResponseDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;

public interface AttendanceService {
	
	/**
	 * 특정 직원의 근태 요약 정보 조회한다.
	 * 
	 * 해당 직원의 이번 달 기준 출근 일수, 지각 횟수, 결근 횟수, 근태 점수 등을 계산하여 반환한다.
	 * @param employeeId 조회할 직원 ID
	 * @return 근태 요약 정보 DTO (출근 일수, 지각 횟수, 결근 횟수, 근태 점수 포함)
	 */
	AttendanceSummaryResponseDTO getAttendanceSummary(Long employeeId);
	
	/**
	 * 특정 직원의 근태 이력 정보 조회한다.
	 * 
	 * 해당 직원의 날짜, 출근 시각, 퇴근 시각, 근무 시간, 상태 등을 포함한 리스트 반환한다.
	 * 
	 * @param employeeId 조회할 직원 ID
	 * @return 근태 이력 리스트 (날짜, 출근 시각, 퇴근 시각, 근무 시간, 상태 포함)
	 */
	AttendanceHistoryPageResponseDTO getAttendanceHistory(Long employeeId, int page, int size);
	
	/**
	 * 특정 직원의 근태 이력 데이터를 엑셀 파일로 생성하여 출력 스트림에 작성한다.
	 * 
	 * 서버에서 엑셀 파일을 생성한 후, OutputStream을 통해 클라이언트로 다운로드할 수 있도록 한다.
	 * 
	 * @param employeeId 엑셀로 출력할 직원 ID
	 * @param outputStream 엑셀 파일 데이터를 작성할 출력 스트림
	 */
	void writeAttendanceExcel(Long employeeId, OutputStream outputStream);
}
