package com.ll.medium_mission.post.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PostRequest(
        @Size(max = 40, message = "제목은 최대 40자 까지만 입력 가능 합니다.")
        @NotEmpty(message = "제목은 필수 항목 입니다.")
        String title,
        @NotEmpty(message = "내용은 필수 항목 입니다.")
        String content,
        Boolean isPaid
){

}
