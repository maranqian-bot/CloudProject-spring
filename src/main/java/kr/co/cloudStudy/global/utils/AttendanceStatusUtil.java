package kr.co.cloudStudy.global.utils;

import kr.co.cloudStudy.attendance.enums.AttendanceStatus;

public class AttendanceStatusUtil {

	// 1. 출근일수 판단
	// - 정상, 지각, 연장 근무
	public static boolean isWorkDays(AttendanceStatus status) {
		return status == AttendanceStatus.NORMAL
				|| status == AttendanceStatus.LATE
				|| status == AttendanceStatus.OVER_TIME;
	}
	
	// 2. 지각 여부
	public static boolean isLate(AttendanceStatus status) {
		return status == AttendanceStatus.LATE;
	}
	
	// 3. 결근 여부
	public static boolean isAbsent(AttendanceStatus status) {
		return status == AttendanceStatus.ABSENT;
	}

}
