package kr.co.cloudStudy.auth.dto;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.cloudStudy.department.entity.Department;
import kr.co.cloudStudy.employee.entity.Employee;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	private final Employee employee;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	@Override
	public String getPassword() {
		return employee.getPassword();
	}
	
	@Override
	public String getUsername() {
		return employee.getEmployeeNumber();
	}
	
	public Long getId() {
		return employee.getEmployeeId();
	}
	
	public String getName() {
		return employee.getName();  
	}
	
	public String getEmployeeNumber() {
		return employee.getEmployeeNumber();
	}
	
	public Department getDepartment() {
		return employee.getDepartment();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
