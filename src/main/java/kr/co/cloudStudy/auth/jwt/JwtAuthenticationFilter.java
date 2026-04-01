package kr.co.cloudStudy.auth.jwt;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.cloudStudy.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private static final Logger log = LogManager.getLogger(JwtAuthenticationFilter.class);
	private static final String BEARER_PREFIX = "Bearer ";
	
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		log.info("Authorization Header = {}", authHeader);
		
		if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
			filterChain.doFilter(request, response);
			return; 
		}
		
		String token = authHeader.substring(7);
		log.info("추출한 토큰 = {}", token);
		
		try {
			if (!jwtUtil.validateToken(token)) {
				log.warn("토큰 검증 실패");
				filterChain.doFilter(request, response);
				return;
			}
			
			String employeeNumber = jwtUtil.getEmployeeNumber(token);
			
			var userDetails = customUserDetailsService.loadUserByUsername(employeeNumber);
			
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities()
					);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("SecurityContext 인증 저장 완료 - employeenumber: {}", employeeNumber);
	
		} catch(Exception e) {
			log.error("JWT 인증 처리 중 예외 발생", e);
			SecurityContextHolder.clearContext();
		}
		
		filterChain.doFilter(request, response);
	}
}
