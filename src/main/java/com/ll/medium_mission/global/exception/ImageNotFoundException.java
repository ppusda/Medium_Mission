package com.ll.medium_mission.global.exception;

public class ImageNotFoundException extends MediumException{

    private static final String MESSAGE = "이미지를 찾을 수 없습니다.";

    public ImageNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
