package kr.co.cloudStudy.global.exception;

public class EmployeeNotFoundException extends RuntimeException {
	
	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
