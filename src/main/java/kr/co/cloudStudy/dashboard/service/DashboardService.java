package kr.co.cloudStudy.dashboard.service;

import kr.co.cloudStudy.dashboard.dto.DashboardRecentActivityResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardResponseDTO;
import kr.co.cloudStudy.dashboard.dto.DashboardTodayAttendanceResponseDTO;

public interface DashboardService {

    DashboardResponseDTO getDashboard(String employeeNumber);

    DashboardTodayAttendanceResponseDTO getTodayAttendance(String employeeNumber);

    DashboardRecentActivityResponseDTO getRecentActivities(String employeeNumber, Integer page, Integer size);

    DashboardTodayAttendanceResponseDTO checkIn(String employeeNumber);

    DashboardTodayAttendanceResponseDTO checkOut(String employeeNumber, Long attendanceId);
}