package com.sptp.dawnary.global.exception;

public class LikeNotFoundException extends IllegalArgumentException {

    private final static String message = "좋아요가 존재하지 않습니다.";
    public LikeNotFoundException() {
        super(message);
    }
}
