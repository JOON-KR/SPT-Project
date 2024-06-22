package com.sptp.dawnary.global.exception;

public class ImageException extends IllegalArgumentException{

    private final static String message = "이미지가 저장/조회에 실패했습니다.";

    public ImageException() {
        super(message);
    }
}
