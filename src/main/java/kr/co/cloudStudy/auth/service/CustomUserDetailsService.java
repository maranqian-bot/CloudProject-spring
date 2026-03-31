package kr.co.cloudStudy.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.cloudStudy.auth.dto.CustomUserDetails;
import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String employeeNumber) throws UsernameNotFoundException {
		EmployeeEntity employee = employeeRepository.findByEmployeeNumber(employeeNumber)
				.orElseThrow(() -> new UsernameNotFoundException("직원을 찾을 수 없습니다."));
		
		return new CustomUserDetails(employee);
	
	}
}
