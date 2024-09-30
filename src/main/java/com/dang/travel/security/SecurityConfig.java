package com.dang.travel.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dang.travel.security.sessionauth.handler.CustomLogoutSuccessHandler;
import com.dang.travel.security.sessionauth.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
			.formLogin(AbstractHttpConfigurer::disable)  // 폼 로그인 비활성화
			.httpBasic(AbstractHttpConfigurer::disable)  // 기본 HTTP 인증 비활성화
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/v1/auth/login", "/v1/auth/check", "/v1/member/signup",
					"/", "/v1/product/**").permitAll()
				.requestMatchers("/v1/auth/logout").authenticated()
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**",
					"/swagger-resources/**",
					"/webjars/**").permitAll()
				.anyRequest().authenticated()

			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // 필요 시 세션 생성
				.sessionFixation().changeSessionId()  // 세션 고정 보호
			)
			.logout(logout -> logout
				.logoutUrl("/api/auth/logout")  // 로그아웃 URL 설정
				.invalidateHttpSession(true)  // 세션 무효화
				.clearAuthentication(true)  // 인증 정보 제거
				.deleteCookies("JSESSIONID")  // JSESSIONID 쿠키 삭제
				.logoutSuccessHandler(customLogoutSuccessHandler)  // 커스텀 로그아웃 성공 핸들러
			)
			.userDetailsService(customUserDetailsService)
			.addFilterBefore(new SecurityContextPersistenceFilter(), BasicAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 변경된 설정
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
		Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
