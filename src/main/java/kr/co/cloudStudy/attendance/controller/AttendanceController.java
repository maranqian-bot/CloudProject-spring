package kr.co.cloudStudy.attendance.controller;

import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.attendance.controller.docs.AttendanceControllerDocs;
import kr.co.cloudStudy.attendance.dto.AttendanceHistoryPageResponseDTO;
import kr.co.cloudStudy.attendance.dto.AttendanceSummaryResponseDTO;
import kr.co.cloudStudy.attendance.service.AttendanceService;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/employees/{employeeId}/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance", description = "직원 근태 조회 API")
public class AttendanceController implements AttendanceControllerDocs {
	
	private final AttendanceService attendanceService;
	
	@Override
	@GetMapping("/summary") 
	public ResponseEntity<ApiResponseDTO<AttendanceSummaryResponseDTO>> getAttendanceSummary(
			@PathVariable("employeeId") Long employeeId) {

		AttendanceSummaryResponseDTO response = attendanceService.getAttendanceSummary(employeeId);
		return ResponseEntity.ok(ApiResponseDTO.success("근태 요약 조회 성공", response));
	}
	
	
	@Override
    @GetMapping("/history")
    public ResponseEntity<ApiResponseDTO<AttendanceHistoryPageResponseDTO>> getAttendanceHistory(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        AttendanceHistoryPageResponseDTO response =
                attendanceService.getAttendanceHistory(employeeId, page, size);

        return ResponseEntity.ok(ApiResponseDTO.success("근태 이력 조회 성공", response));
    }
	
	
	@Override
	@GetMapping("/excel")
	public ResponseEntity<StreamingResponseBody> downloadAttendanceExcel(
	        @PathVariable("employeeId") Long employeeId) {

	    String fileName = "근태이력_" + LocalDate.now() + ".xlsx";
	    String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
	            .replaceAll("\\+", "%20");

	    HttpHeaders headers = new HttpHeaders();
	    headers.add(
	            HttpHeaders.CONTENT_TYPE,
	            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
	    );
	    headers.setContentDisposition(
	            ContentDisposition.attachment()
	                    .filename(encodedFileName, StandardCharsets.UTF_8)
	                    .build()
	    );

	    StreamingResponseBody responseBody = outputStream -> {
	        try {
	        	
	            attendanceService.writeAttendanceExcel(employeeId, outputStream);
	            
	            outputStream.flush();
	            
	        } catch (Exception e) {
	        	
	            e.printStackTrace();
	            
	            throw new RuntimeException("근태 엑셀 다운로드 중 오류가 발생했습니다.", e);
	        }
	    };

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(responseBody);
	}


	
	
	
}
