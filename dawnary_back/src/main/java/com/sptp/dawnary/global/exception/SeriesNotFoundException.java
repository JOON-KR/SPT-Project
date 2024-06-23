package com.sptp.dawnary.global.exception;

public class SeriesNotFoundException extends IllegalArgumentException{

    private final static String message = "시리즈가 존재하지 않습니다.";

    public SeriesNotFoundException() {
        super(message);
    }
}
