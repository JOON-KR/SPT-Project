package com.sptp.dawnary.global.exception;

public class AlreadyExistsLikeException extends IllegalArgumentException{

    private final static String message = "이미 좋아요를 눌렀습니다.";

    public AlreadyExistsLikeException() {
        super(message);
    }
}
