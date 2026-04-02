package kr.co.cloudStudy.auth.service;

/**
 * 이메일 발송 관련 서비스 인터페이스
 */
public interface MailService {
	
	/**
     * 테스트용 이메일을 발송한다.
     * 
     * 이메일 전송 기능이 정상적으로 동작하는지 확인하기 위한 용도이다.
     * 주로 개발 환경에서 SMTP 설정 검증 시 사용된다.
     * 
     * @param toEmail 수신자 이메일 주소
     */
	void sendTestMail(String toEmail);
	
	/**
     * 인증 코드를 포함한 이메일을 발송한다.
     * 
     * 회원가입 또는 비밀번호 재설정 과정에서 사용되며,
     * 생성된 인증 코드를 이메일 내용에 포함하여 전달한다.
     * 
     * @param toEmail 수신자 이메일 주소
     * @param code 발송할 인증 코드
     */
	void sendEmailCode(String toEmail, String code);
}
