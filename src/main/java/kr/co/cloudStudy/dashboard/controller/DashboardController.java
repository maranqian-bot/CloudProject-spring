package kr.co.cloudStudy.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.cloudStudy.auth.dto.CustomUserDetails;
import kr.co.cloudStudy.dashboard.controller.docs.DashboardControllerDocs;
import kr.co.cloudStudy.dashboard.dto.DashboardRecentActivityResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardTodayAttendanceResponseDTO;
import kr.co.cloudStudy.dashboard.service.DashboardService;
import kr.co.cloudStudy.global.dto.ApiResponseDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "대시보드 API")
public class DashboardController implements DashboardControllerDocs {

    private final DashboardService dashboardService;

    @Override
    @GetMapping
    public ResponseEntity<ApiResponseDTO<DashboardResponseDTO>> getDashboard(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        DashboardResponseDTO response =
                dashboardService.getDashboard(extractEmployeeNumber(userDetails));

        return ResponseEntity.ok(
                ApiResponseDTO.success("대시보드 조회 성공", response)
        );
    }

    @Override
    @GetMapping("/attendance/today")
    public ResponseEntity<ApiResponseDTO<DashboardTodayAttendanceResponseDTO>> getTodayAttendance(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        DashboardTodayAttendanceResponseDTO response =
                dashboardService.getTodayAttendance(extractEmployeeNumber(userDetails));

        return ResponseEntity.ok(
                ApiResponseDTO.success("오늘 근태 상태 조회 성공", response)
        );
    }

    @Override
    @GetMapping("/attendance/history")
    public ResponseEntity<ApiResponseDTO<DashboardRecentActivityResponseDTO>> getRecentActivities(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        DashboardRecentActivityResponseDTO response =
                dashboardService.getRecentActivities(
                        extractEmployeeNumber(userDetails),
                        page,
                        size
                );

        return ResponseEntity.ok(
                ApiResponseDTO.success("최근 활동 조회 성공", response)
        );
    }

    @Override
    @PostMapping("/attendance/check-in")
    public ResponseEntity<ApiResponseDTO<DashboardTodayAttendanceResponseDTO>> checkIn(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        DashboardTodayAttendanceResponseDTO response =
                dashboardService.checkIn(extractEmployeeNumber(userDetails));

        return ResponseEntity.ok(
                ApiResponseDTO.success("출근 처리 성공", response)
        );
    }

    @Override
    @PatchMapping("/attendance/{attendanceId}/check-out")
    public ResponseEntity<ApiResponseDTO<DashboardTodayAttendanceResponseDTO>> checkOut(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long attendanceId
    ) {
        DashboardTodayAttendanceResponseDTO response =
                dashboardService.checkOut(
                        extractEmployeeNumber(userDetails),
                        attendanceId
                );

        return ResponseEntity.ok(
                ApiResponseDTO.success("퇴근 처리 성공", response)
        );
    }

    private String extractEmployeeNumber(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("인증 정보가 없습니다.");
        }
        return userDetails.getEmployeeNumber();
    }
}