package com.hyunbenny.security.exception;

import java.util.Map;

public class UserExistException extends CustomException{

    private static final String MESSAGE = "해당 회원이 이미 존재합니다.";

    @Override
    public int getStatusCode() {
        return 400;
    }

    public UserExistException() {
        super(MESSAGE);
    }

    public UserExistException(String message) {
        super(message);
    }

    public UserExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
