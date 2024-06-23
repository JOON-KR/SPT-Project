package com.sptp.dawnary.global.exception;

public class EmptyImageException extends IllegalArgumentException{

    private final static String message = "이미지가 존재하지 않습니다.";

    public EmptyImageException() {
        super(message);
    }
}
