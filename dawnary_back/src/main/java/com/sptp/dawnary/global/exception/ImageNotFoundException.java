package com.sptp.dawnary.global.exception;

public class ImageNotFoundException extends IllegalArgumentException {

    private final static String message = "이미지가 존재하지 않습니다.";
    public ImageNotFoundException() {
        super(message);
    }
}
