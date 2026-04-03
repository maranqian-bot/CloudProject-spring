package kr.co.cloudStudy.auth.service;

/**
 * 이메일 인증 및 비밀번호 재설정 관련 서비스 인터페이스
 */
public interface EmailCodeService {

	/**
     * 입력한 이메일로 인증 코드를 전송한다.
     * 
     * 랜덤한 인증 코드를 생성한 후 이메일로 발송하고,
     * 해당 코드는 Redis 등에 일정 시간 동안 저장된다.
     * 
     * @param email 인증 코드를 받을 이메일 주소
     */
	void sendEmailCode(String email);
	
	/**
     * 사용자가 입력한 인증 코드가 유효한지 검증한다.
     * 
     * Redis 등에 저장된 인증 코드와 비교하여 일치 여부를 확인하고,
     * 인증 성공 시 해당 이메일을 인증 완료 상태로 처리한다.
     * 
     * @param email 인증을 진행할 이메일 주소
     * @param code 사용자가 입력한 인증 코드
     */
	void verifyEmailCode(String email, String code);
	
	/**
     * 인증이 완료된 이메일에 대해 비밀번호를 재설정한다.
     * 
     * 새로운 비밀번호는 BCrypt 등을 통해 암호화한 후 저장되며,
     * 인증되지 않은 이메일의 경우 예외를 발생시킨다.
     * 
     * @param email 비밀번호를 변경할 이메일 주소
     * @param newPassword 새 비밀번호
     */
	void resetPassword(String email, String newPassword);
	
}
