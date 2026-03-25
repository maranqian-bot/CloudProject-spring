package kr.co.cloudStudy.global.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.co.cloudStudy.attendance.dto.AttendanceHistoryResponseDTO;
import kr.co.cloudStudy.attendance.enums.AttendanceStatus;

public class AttendanceExcelUtil {
	
	private AttendanceExcelUtil() {
	}
	
//	Workbook = 엑셀 파일 전체
//	Sheet = 엑셀 안의 시트 1장
//	Row = 한 줄
//	Cell = 한 칸
	public static byte[] createAttendanceExcel(List<AttendanceHistoryResponseDTO> attendanceList) {
		
		// 엑셀 파일 한 개 생성(.xlsx 파일 만들겠다)
		try (Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			
			// 시트 추가
			Sheet sheet = workbook.createSheet("근태내역");
			
			// 컬럼 너비 설정
			sheet.setColumnWidth(0, 3000); // 번호
			sheet.setColumnWidth(1, 5000); // 근무일자
			sheet.setColumnWidth(2, 5000); // 출근시각
			sheet.setColumnWidth(3, 5000); // 퇴근시각
			sheet.setColumnWidth(4, 5000); // 총근무시간
			sheet.setColumnWidth(5, 5000); // 상태
			
			// 헤더 추가 (제목)
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("번호");
			headerRow.createCell(1).setCellValue("근무일자");
			headerRow.createCell(2).setCellValue("출근시각");
			headerRow.createCell(3).setCellValue("퇴근시각");
			headerRow.createCell(4).setCellValue("총근무시간");
			headerRow.createCell(5).setCellValue("상태");
			
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			
			// 데이터 행 생성 (근태 목록을 한 줄씩 엑셀에 넣음)
			for (int i = 0; i < attendanceList.size(); i++) {
				// list에서 하나씩 꺼내서 item에 저장
				AttendanceHistoryResponseDTO item = attendanceList.get(i);
				Row row = sheet.createRow(i + 1);
				
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue(
				        item.getWorkDate() != null ? item.getWorkDate().format(dateFormatter) : ""
				);
				row.createCell(2).setCellValue(
				        item.getCheckInTime() != null ? item.getCheckInTime().format(timeFormatter) : ""
				);
				row.createCell(3).setCellValue(
				        item.getCheckOutTime() != null ? item.getCheckOutTime().format(timeFormatter) : ""
				);
				row.createCell(4).setCellValue(
				        item.getWorkMinutes() != null ? item.getWorkMinutes() : 0
				);
				row.createCell(5).setCellValue(
				        item.getAttendanceStatus() != null ? getStatusLabel(item.getAttendanceStatus()) : ""
				);
			}
			
			workbook.write(out);
			return out.toByteArray(); // 엑셀 파일을 다운로드 가능한 형태로 바꿔서 반환
			
		} catch (IOException e) {
			throw new RuntimeException("엑셀 파일 생성 중 오류가 발생했습니다.", e);
		}
	}
	
	private static String getStatusLabel(AttendanceStatus status) {
		if (status == null) {
			return "";
		}
		
		return switch (status) {
			case NORMAL -> "정상";
			case LATE -> "지각";
			case EARLY_LEAVE -> "조퇴";
			case VACATION -> "휴가";
			case OVER_TIME -> "연장 근무";
		default -> throw new IllegalArgumentException("Unexpected value: " + status);
		};
	}
	
	private static String formatWorkMinutes(Integer workMinutes) {
		if (workMinutes == null) {
			return "";
		}
		
		int hours = workMinutes / 60;
		int minutes = workMinutes % 60;
		
		return hours + "시간 " + minutes + "분";
	}
	

}
