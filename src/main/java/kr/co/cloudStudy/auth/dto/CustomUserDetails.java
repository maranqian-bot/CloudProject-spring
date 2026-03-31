package kr.co.cloudStudy.auth.dto;


import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.cloudStudy.employee.entity.EmployeeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	private final EmployeeEntity employee;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
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
		return employee.getId();
	}
	
	public String getName() {
		return employee.getName();  
	}
	
	public String getEmployeeNumber() {
		return employee.getEmployeeNumber();
	}
	
	public Long getDeptId() {
		return employee.getDeptId();
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
