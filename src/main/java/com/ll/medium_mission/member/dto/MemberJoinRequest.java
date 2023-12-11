package com.ll.medium_mission.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record MemberJoinRequest (
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수로 입력해야 합니다.")
        String email,
        @NotBlank(message = "비밀번호는 필수로 입력해야 합니다.")
        String password,
        @NotBlank(message = "비밀번호 확인은 필수로 입력해야 합니다.")
        String passwordConfirm){

}
