package com.sptp.dawnary.global.exception;

public class SameMemberException extends IllegalArgumentException {

	private static final String MESSAGE = "자기자신을 대상으로 할 수 없습니다.";

	public SameMemberException() {
		super(MESSAGE);
	}

	public int getStatusCode() {
		return 500;
	}
}
