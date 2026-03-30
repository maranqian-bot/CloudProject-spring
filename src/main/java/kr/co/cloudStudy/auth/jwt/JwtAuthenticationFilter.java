package kr.co.cloudStudy.auth.jwt;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		System.out.println("Authorization Header = " + authHeader);
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authHeader.substring(7);
		System.out.println("추출한 토큰 = " + token);
		
		if (!jwtUtil.validateToken(token)) {
			System.out.println("토큰 검증 실패");
			filterChain.doFilter(request, response);
			return;
		}
		
		String employeeNumber = jwtUtil.getEmployeeNumber(token);
		
		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(employeeNumber, null, Collections.emptyList());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("SecurityContext 인증 저장 완료");
		
		filterChain.doFilter(request, response);
	}
}
