package kr.co.cloudStudy.auth.service.impl;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.cloudStudy.auth.repository.EmailCodeRedisRepository;
import kr.co.cloudStudy.auth.service.EmailCodeService;
import kr.co.cloudStudy.auth.service.MailService;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailCodeServiceImpl implements EmailCodeService {
	
	private final EmailCodeRedisRepository emailCodeRedisRepository;
	private final MailService mailService;
	private final EmployeeRepository employeeRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void sendEmailCode(String email) {
		
		if (emailCodeRedisRepository.isSendBlocked(email)) {
			throw new IllegalArgumentException("인증번호는 잠시 후 다시 요청해주세요.");
		}
		String code = createRandomCode();
		
		emailCodeRedisRepository.saveEmailCode(email, code);
		emailCodeRedisRepository.deleteVerified(email);
		emailCodeRedisRepository.saveSendBlock(email);
		mailService.sendEmailCode(email, code);
		
	}
	
	@Override
	public void verifyEmailCode(String email, String code) {
		String savedCode = emailCodeRedisRepository.getEmailCode(email);
		
		if (savedCode == null) {
			throw new IllegalArgumentException("인증코드가 없거나 만료되었습니다.");
		}
		
		if (!savedCode.equals(code)) {
			throw new IllegalArgumentException("인증코드가 일치하지 않습니다.");
		}
		
		emailCodeRedisRepository.saveVerified(email);
		emailCodeRedisRepository.deleteEmailCode(email);
		
	}
	
	@Override
	public void resetPassword(String email, String newPassword) {
		Employee employee = employeeRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다."));
		
		if (!emailCodeRedisRepository.isVerified(email)) {
			throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
		}
		
		validatePassword(newPassword);
		
//		TODO: 나중에 반드시 복구 (비밀번호 암호화 적용)
//		String encodedPassword = passwordEncoder.encode(newPassword);
//		employee.setPassword(encodedPassword);
		
        employee.setPassword(newPassword);
        
        emailCodeRedisRepository.deleteVerified(email);
	}
	
	private String createRandomCode() {
		SecureRandom secureRandom = new SecureRandom();
		int number = 100000 + secureRandom.nextInt(900000);
		return String.valueOf(number);
	}
	
	private void validatePassword(String password) {
	    String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$";

	    if (!password.matches(regex)) {
	        throw new IllegalArgumentException("비밀번호는 8자 이상이며 영문, 숫자, 특수문자를 포함해야 합니다.");
	    }
	}
}

