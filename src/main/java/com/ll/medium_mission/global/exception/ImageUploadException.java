package com.ll.medium_mission.global.exception;

public class ImageUploadException extends MediumException{

    private static final String MESSAGE = "이미지를 업로드 할 수 없습니다.";

    public ImageUploadException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
