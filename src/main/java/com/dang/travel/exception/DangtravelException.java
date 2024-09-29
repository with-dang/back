package com.dang.travel.exception;

public class DangtravelException extends RuntimeException {
	public DangtravelException() {
		super();
	}

	public DangtravelException(String message) {
		super(message);
	}

	public DangtravelException(String message, Throwable cause) {
		super(message, cause);
	}

	public DangtravelException(Throwable cause) {
		super(cause);
	}
}
