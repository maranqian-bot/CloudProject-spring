package kr.co.cloudStudy.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.DispatcherType;
import kr.co.cloudStudy.auth.jwt.JwtAuthenticationFilter;
import kr.co.cloudStudy.auth.jwt.JwtUtil;
import kr.co.cloudStudy.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    
	    http
	        .cors(Customizer.withDefaults())
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	                .dispatcherTypeMatchers(DispatcherType.ASYNC, DispatcherType.ERROR).permitAll()
	                
	                /* [테스트를 위한 로그인 무효화 설정] */
	                .anyRequest().permitAll() 
	                
	                /* 원복 시 아래 주석 해제 및 위 line 삭제
	                .requestMatchers(
	                    "/api/auth/**",
	                    "/swagger-ui/**", 
	                    "/v3/api-docs/**"
	                ).permitAll()
	                .anyRequest().authenticated()
	                */
	            ); // <--- 여기서 세미콜론으로 닫아야 합니다.

	            /* [테스트를 위해 JWT 필터 일시 비활성화] - 아래는 완전히 주석 처리됨
	            http.addFilterBefore(
	                    new JwtAuthenticationFilter(jwtUtil, customUserDetailsService),
	                    UsernamePasswordAuthenticationFilter.class);
	            */

	    return http.build();
	}
	
	// 패스워드 암호화
	@Bean 
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 리액트에서 오는 요청 허용하는 CORS 설정
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowedOrigins(List.of("http://localhost:5173")); // 리액트 주소 (배포시 설정)
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true); 
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}