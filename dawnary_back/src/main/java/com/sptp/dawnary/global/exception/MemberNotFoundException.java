package com.sptp.dawnary.global.exception;

public class MemberNotFoundException extends IllegalArgumentException{

    private static final String MESSAGE = "멤버가 존재하지 않습니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
