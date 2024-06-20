package com.sptp.dawnary.global.exception;

public class DiaryNotFoundException extends IllegalArgumentException{

    public DiaryNotFoundException(String message) {
        super(message);
    }
}
