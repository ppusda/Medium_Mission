package com.ll.medium_mission.member.validator;

import com.ll.medium_mission.member.dto.MemberJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator {

    private final static String JOIN_ERROR = "JOIN_ERROR";

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberJoinRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberJoinRequest request = (MemberJoinRequest) target;

        if (!request.password().equals(request.passwordConfirm())) {
            errors.rejectValue("passwordConfirm", JOIN_ERROR, "비밀번호가 일치하지 않습니다.");
        }
    }
}
