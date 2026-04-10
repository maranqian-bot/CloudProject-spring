package kr.co.cloudStudy.auth.oauth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.cloudStudy.auth.jwt.JwtUtil;
import kr.co.cloudStudy.employee.entity.Employee;
import kr.co.cloudStudy.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final EmployeeRepository employeeRepository;
	private final JwtUtil jwtUtil;
	
	@Value("${app.frontend.url}")
	private String frontendUrl;
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication
	) throws IOException, ServletException {
		
		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
		String email = (String) oAuth2User.getAttributes().get("email");
		
		Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("소셜 로그인 사용자를 찾을 수 없습니다."));
	
		String accessToken = jwtUtil.createAccessToken(
				employee.getEmployeeId(),
				employee.getEmployeeNumber(),
				employee.getName()
		);
	}
	
	
	
	
}
