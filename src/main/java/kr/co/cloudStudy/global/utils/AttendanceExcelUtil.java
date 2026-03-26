package kr.co.cloudStudy.global.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import kr.co.cloudStudy.attendance.dto.AttendanceExcelRowDTO;

public class AttendanceExcelUtil {
	
//	Workbook = 엑셀 파일 전체
//	Sheet = 엑셀 안의 시트 1장
//	Row = 한 줄
//	Cell = 한 칸
	public static void writeAttendanceExcel(
			List<AttendanceExcelRowDTO> attendanceList, // 엑셀 형식으로 변환된 엑셀용 dto리스트
			OutputStream outputStream) { // 파일을 어디에 쓸지 알려주는 통로(지금은 브라우저 다운로드)
		
		// 스트리밍 방식 엑셀, 엑셀 파일 (.xlsx)         메모리에 유지할 row수
		// XSSFWorkbook: 메모리에 다 올려서 OOM 위험
		// SXSSFWorkbook: 일부만 메모리에 유지해서 안전함
		SXSSFWorkbook workbook = new SXSSFWorkbook(100); 
		
		try {
			
			// 시트 생성
			Sheet sheet = workbook.createSheet("근태내역");
			setColumnWidths(sheet);
			createHeaderRow(sheet);
			createDataRows(sheet, attendanceList);
			
			workbook.write(outputStream); // 엑셀 파일 작성 (엑셀 파일 -> 브라우저 전달)
			outputStream.flush(); // 남아있는 데이터 강제로 다 밀어냄
			
		} catch (IOException e) {
			throw new RuntimeException("엑셀 파일 생성 중 오류가 발생했습니다.", e);
		} finally {
			workbook.dispose(); // 임시 파일 정리 (SXSSF 필수)
			try {
				workbook.close(); // 리소스 해제
			} catch (IOException e) {
				throw new RuntimeException("엑셀 워크북 종류 중 오류가 발생했습니다.", e);
			}
		}
	}
			
	// 컬럼 너비 설정용 메서드
	private static void setColumnWidths(Sheet sheet) {
		sheet.setColumnWidth(0, 3000); // 번호
		sheet.setColumnWidth(1, 5000); // 근무일자
		sheet.setColumnWidth(2, 5000); // 출근시각
		sheet.setColumnWidth(3, 5000); // 퇴근시각
		sheet.setColumnWidth(4, 5000); // 총근무시간
		sheet.setColumnWidth(5, 5000); // 상태
	}
	
	// 
	private static void createHeaderRow(Sheet sheet) {
		// 헤더 추가 (제목)
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("번호");
		headerRow.createCell(1).setCellValue("근무일자");
		headerRow.createCell(2).setCellValue("출근시각");
		headerRow.createCell(3).setCellValue("퇴근시각");
		headerRow.createCell(4).setCellValue("총근무시간");
		headerRow.createCell(5).setCellValue("상태");
	}
			
	// 데이터 행 생성하는 메소드
	// attendList(엑셀용dto리스트)에서 한줄씩 꺼냄
	// 헤더 다음부터 넣어야 하니까 i + 1
	private static void createDataRows(Sheet sheet, List<AttendanceExcelRowDTO> attendanceList)  {
		for (int i = 0; i < attendanceList.size(); i++) {
            AttendanceExcelRowDTO item = attendanceList.get(i);
            Row row = sheet.createRow(i + 1);

            // 데이터 채우기
            createDataRow(row, i + 1, item);
        }
	
	}
	
	// 데이터 채우기용 메서드
	private static void createDataRow(Row row, int rowNumber, AttendanceExcelRowDTO item) {
        row.createCell(0).setCellValue(rowNumber);
        row.createCell(1).setCellValue(defaultString(item.getWorkDate()));
        row.createCell(2).setCellValue(defaultString(item.getCheckInTime()));
        row.createCell(3).setCellValue(defaultString(item.getCheckOutTime()));
        row.createCell(4).setCellValue(defaultInteger(item.getWorkMinutes()));
        row.createCell(5).setCellValue(defaultString(item.getAttendanceStatusLabel()));
    }

	// null 넣으면 엑셀 깨지니까
    private static String defaultString(String value) {
        return value != null ? value : "";
    }

    private static int defaultInteger(Integer value) {
        return value != null ? value : 0;
    }
}
