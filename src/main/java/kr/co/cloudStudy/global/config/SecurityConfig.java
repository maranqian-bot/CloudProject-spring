package kr.co.cloudStudy.global.config;

import org.springframework.security.config.Customizer;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//CSRF 비활성화
		http
			.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable())
			// 모든 요청 허용
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(
						"/api/auth/**",
						"/swagger-ui/**",
						"v3/api-docs/**"
					).permitAll()
					.anyRequest().permitAll());
		
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
		// 세션, 쿠키 전달 허용
		config.setAllowCredentials(true); 
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		// 전체 경로 적용
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}

