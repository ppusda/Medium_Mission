package com.ll.medium_mission.member.controller;

import com.ll.medium_mission.global.util.ValidateUtil;
import com.ll.medium_mission.member.dto.MemberCheckResponse;
import com.ll.medium_mission.member.dto.MemberJoinRequest;
import com.ll.medium_mission.member.dto.MemberLoginRequest;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.member.validator.JoinValidator;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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

        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        memberService.join(memberJoinRequest.email(), memberJoinRequest.nickname(), memberJoinRequest.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@Valid MemberLoginRequest memberLoginRequest, BindingResult bindingResult) {
        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        return ResponseEntity.ok(memberService.login(memberLoginRequest.email(), memberLoginRequest.password()));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/logout")
    public void logoutMember(Principal principal) {
        SecurityContextHolder.clearContext();
        memberService.logout(principal.getName());
    }

    @GetMapping("/check")
    public MemberCheckResponse checkMember(Principal principal) {
        if (principal != null) {
            Member member = memberService.getMember(principal.getName());

            return MemberCheckResponse.builder()
                    .nickname(member.getNickname())
                    .result(true)
                    .build();
        }

        return MemberCheckResponse.builder()
                .result(false)
                .build();
    }
}
