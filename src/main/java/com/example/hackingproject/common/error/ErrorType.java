package com.example.hackingproject.common.error;

import org.springframework.http.HttpStatus;

public enum ErrorType {

	internal_error(ErrorCategory.common, -10001, "서버 내부 에러"),
	unauthorized(ErrorCategory.common, -10002, "권한이 없습니다"),

	user_auth_fail(ErrorCategory.user, 10000, "회원 인증에 실패하였습니다."),
	user_no_registered(ErrorCategory.user, 10002, "등록된 정보가 없습니다."),
	already_user(ErrorCategory.user, 10001, "이미 가입된 회원입니다."),
	invalid_authentication_number(ErrorCategory.user, 10003, "유효하지 않은 인증번호 입니다."),
	timeout_authentication_number(ErrorCategory.user, 10004, "인증번호 입력 시간이 초과 되었습니다."),
	login_auth_fail_no_registered_user(ErrorCategory.user, 10005, "해당 휴대폰번호는 회원가입되지 않았습니다. 회원가입 후에 로그인해 주시기 바랍니다."),
	user_email_duplicate(ErrorCategory.user, 10006, "이미 사용중인 이메일입니다."),
	user_delete_fail_exist_reserve(ErrorCategory.user, 10007, "선택한 고객은 완료되지 않은 모빌리티 예약 건이 존재하여 삭제 불가합니다.\n예약 정보 확인 바랍니다."),

	token_parse_error(ErrorCategory.auth, 20001, "유효하지 않은 토큰 입니다."),
	fail_to_access(ErrorCategory.auth, 20003, "잘못된 접근입니다."),
	do_not_have_authority(ErrorCategory.auth, 20004, "권한이 없습니다."),

	auth_fail(ErrorCategory.admin, 10000, "로그인에 실패하였습니다. 로그인 정보를 다시 확인해주세요."),
	no_registered_user(ErrorCategory.admin, 10002, "등록된 정보가 없습니다."),


	invalidate_request_parameter(ErrorCategory.common, 40000, "파라미터가 유효하지 않습니다."),
	missing_request_parameter(ErrorCategory.common, 40001, "필수 파라미터가 누락되었습니다."),
	insert_database_fail(ErrorCategory.common, 40002, "DB 입력 실패"),

	;

	public ErrorCategory category;
	public int errorCode;
	public String message;
	public HttpStatus httpStatus;

	ErrorType(ErrorCategory category, int errorCode, String message, HttpStatus httpStatus) {
		this.category = category;
		this.errorCode = errorCode;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	ErrorType(ErrorCategory category, int errorCode, String message) {
		this.category = category;
		this.errorCode = errorCode;
		this.message = message;
		this.httpStatus = HttpStatus.OK;
	}
}
