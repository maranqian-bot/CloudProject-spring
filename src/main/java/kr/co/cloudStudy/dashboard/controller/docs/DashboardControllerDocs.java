package kr.co.cloudStudy.dashboard.controller.docs;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.co.cloudStudy.auth.dto.CustomUserDetails;
import kr.co.cloudStudy.dashboard.dto.DashboardRecentActivityResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardTodayAttendanceResponseDTO;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;

public interface DashboardControllerDocs {

    @Operation(
            summary = "대시보드 메인 데이터 조회",
            description = "현재 로그인 사용자의 대시보드 메인 데이터를 조회합니다."
    )
    ResponseEntity<ApiResponseDTO<DashboardResponseDTO>> getDashboard(
            @Parameter(hidden = true)
            CustomUserDetails userDetails
    );

    @Operation(
            summary = "오늘 출근/퇴근 상태 조회",
            description = "현재 로그인 사용자의 오늘 출근/퇴근 상태를 조회합니다."
    )
    ResponseEntity<ApiResponseDTO<DashboardTodayAttendanceResponseDTO>> getTodayAttendance(
            @Parameter(hidden = true)
            CustomUserDetails userDetails
    );

    @Operation(
            summary = "최근 활동 조회",
            description = "현재 로그인 사용자의 최근 근태 활동 목록을 페이지 단위로 조회합니다."
    )
    ResponseEntity<ApiResponseDTO<DashboardRecentActivityResponseDTO>> getRecentActivities(
            @Parameter(hidden = true)
            CustomUserDetails userDetails,

            @Parameter(description = "페이지 번호(1부터 시작)", example = "1")
            Integer page,

            @Parameter(description = "페이지 크기", example = "5")
            Integer size
    );

    @Operation(
            summary = "출근 처리",
            description = "현재 로그인 사용자의 오늘 출근을 처리합니다."
    )
    ResponseEntity<ApiResponseDTO<DashboardTodayAttendanceResponseDTO>> checkIn(
            @Parameter(hidden = true)
            CustomUserDetails userDetails
    );

    @Operation(
            summary = "퇴근 처리",
            description = "현재 로그인 사용자의 오늘 퇴근을 처리합니다."
    )
    ResponseEntity<ApiResponseDTO<DashboardTodayAttendanceResponseDTO>> checkOut(
            @Parameter(hidden = true)
            CustomUserDetails userDetails,

            @Parameter(description = "근태 기록 ID", example = "15")
            Long attendanceId
    );
}