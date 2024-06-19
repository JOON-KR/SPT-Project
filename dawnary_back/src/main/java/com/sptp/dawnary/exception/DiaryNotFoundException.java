package com.sptp.dawnary.exception;

public class DiaryNotFoundException extends IllegalArgumentException{

    public DiaryNotFoundException(String message) {
        super(message);
    }
}
