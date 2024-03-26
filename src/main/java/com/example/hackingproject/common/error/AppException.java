package com.example.hackingproject.common.error;

public class AppException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7178945192162789217L;

	private ErrorType errorType;

	public AppException(ErrorType type) {
		this(type, null);
	}
	
	public AppException(ErrorType type, String customMessage) {
		super();
		
		errorType = type;
		if (customMessage != null) {
			errorType.message = customMessage;
		}
	}

	public AppException(ErrorType type, String customMessage, Throwable ex) {
		super(ex);
		
		errorType = type;
		if (customMessage != null) {
			errorType.message = customMessage;
		}
	}
	
	public ErrorType getErrorType() {
		return errorType;
	}
}
