package kr.co.cloudStudy.auth.service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	
	private final EmployeeRepository employeeRepository;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		String email = (String) oAuth2User.getAttributes().get("email");
		String name = (String) oAuth2User.getAttributes().get("name");
		
		if (email == null || email.isBlank()) {
			throw new OAuth2AuthenticationException("구글 이메일 정보를 가져올 수 없습니다.");
		}
		saveOrUpdate(email, name);
		
		return new DefaultOAuth2User(
				Collections.singleton(() -> "ROLE_USER"),
				oAuth2User.getAttributes(),
				"email"
		);
	}
	
	private Employee saveOrUpdate(String email, String name) {
		Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
		
		if (optionalEmployee.isPresent()) {
			Employee employee = optionalEmployee.get();
			
			if (name != null && !name.isBlank()) {
				employee.setName(name);
			}
			
			return employeeRepository.save(employee);
		}
		
		Employee newEmployee = Employee.builder()
				.employeeNumber("GOOGLE_" + UUID.randomUUID().toString().substring(0, 8))
				.name(name != null ? name : "Google User")
				.email(email)
				.password("SOCIAL_LOGIN_USER")
				.build();
		
		return employeeRepository.save(newEmployee);  
	}
	
}
