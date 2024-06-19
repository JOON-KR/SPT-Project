package com.sptp.dawnary.global.exception;

public class MemberNotFoundException extends IllegalArgumentException{

    public MemberNotFoundException(String message) {
        super(message);
    }
}
