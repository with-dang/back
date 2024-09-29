package com.dang.travel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("잘못된 자격 증명입니다.");
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<String> handleDisabledException(DisabledException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("사용자가 비활성화되었습니다.");
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<String> handleLockedException(LockedException e) {
		return ResponseEntity.status(HttpStatus.LOCKED).body("계정이 잠겼습니다.");
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: 필요한 데이터가 없습니다.");
	}

	// @ExceptionHandler(Exception.class)
	// public ResponseEntity<String> handleGeneralException(Exception e) {
	// 	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
	// }
}
