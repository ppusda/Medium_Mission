package com.ll.medium_mission.global.exception;

public class AlreadyExistMemberException extends MediumException{

    private static final String MESSAGE = "이미 존재하는 회원입니다.";

    public AlreadyExistMemberException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
