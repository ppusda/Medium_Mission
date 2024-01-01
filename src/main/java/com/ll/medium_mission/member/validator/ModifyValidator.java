package com.ll.medium_mission.member.validator;

import com.ll.medium_mission.member.dto.MemberJoinRequest;
import com.ll.medium_mission.member.dto.MemberModifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ModifyValidator implements Validator {

    private final static String VALIDATE_ERROR = "VALIDATE_ERROR";

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberModifyRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberModifyRequest request = (MemberModifyRequest) target;

        if (!request.newPassword().equals(request.newPasswordConfirm())) {
            errors.rejectValue("newPasswordConfirm", VALIDATE_ERROR, "새 비밀번호가 일치하지 않습니다.");
        }
    }
}
