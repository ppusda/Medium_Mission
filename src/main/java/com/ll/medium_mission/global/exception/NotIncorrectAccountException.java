package com.ll.medium_mission.global.exception;

public class NotIncorrectAccountException extends MediumException{

    private static final String MESSAGE = "아이디/또는 비밀번호가 틀립니다.";

    public NotIncorrectAccountException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
