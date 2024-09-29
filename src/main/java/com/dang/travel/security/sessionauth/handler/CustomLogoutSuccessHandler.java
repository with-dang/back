package com.dang.travel.security.sessionauth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		throws IOException {

		// 세션 무효화 로그 출력
		if (request.getSession(false) == null) {
			System.out.println("세션이 성공적으로 무효화되었습니다.");
		} else {
			System.out.println("세션이 아직 활성화되어 있습니다.");
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().write("{\"message\": \"logout success\"}");
		response.getWriter().flush();
	}
}
