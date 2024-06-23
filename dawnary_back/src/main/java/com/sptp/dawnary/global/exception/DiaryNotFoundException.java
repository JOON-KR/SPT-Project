package com.sptp.dawnary.global.exception;

public class DiaryNotFoundException extends IllegalArgumentException{

    private final static String message = "존재하지 않는 다이어리입니다.";

    public DiaryNotFoundException() {
        super(message);
    }
}
