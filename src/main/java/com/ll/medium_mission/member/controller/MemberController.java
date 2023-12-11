package com.ll.medium_mission.member.controller;

import com.ll.medium_mission.global.util.ValidateUtil;
import com.ll.medium_mission.member.dto.MemberJoinRequest;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.member.validator.JoinValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JoinValidator joinValidator;
    private final ValidateUtil validateUtil;

    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@Valid MemberJoinRequest memberJoinRequest, BindingResult bindingResult) {
        joinValidator.validate(memberJoinRequest, bindingResult);

        if (bindingResult.hasErrors()) {
            return validateUtil.getErrors(bindingResult);
        }

        memberService.join(memberJoinRequest.email(), memberJoinRequest.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public void loginMember() {

    }

    @PostMapping("/logout")
    public void logoutMember() {

    }
}
