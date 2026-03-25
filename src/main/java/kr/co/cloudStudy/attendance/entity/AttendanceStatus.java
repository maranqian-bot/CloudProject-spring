package kr.co.cloudStudy.attendance.entity;

// 정상, 지각, 조퇴, 휴가, 연장 근무
public enum AttendanceStatus {
	NORMAL,
	LATE,
	EARLYLEAVE,
	VACATION,
	ABSENT,
	OVERTIME
}
