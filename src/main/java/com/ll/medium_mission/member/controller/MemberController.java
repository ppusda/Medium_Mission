package com.ll.medium_mission.member.controller;

import com.ll.medium_mission.global.provider.CookieProvider;
import com.ll.medium_mission.global.util.ValidateUtil;
import com.ll.medium_mission.member.dto.MemberCheckResponse;
import com.ll.medium_mission.member.dto.MemberJoinRequest;
import com.ll.medium_mission.member.dto.MemberLoginRequest;
import com.ll.medium_mission.member.dto.MemberLoginResponse;
import com.ll.medium_mission.member.dto.MemberModifyRequest;
import com.ll.medium_mission.member.dto.MemberResponse;
import com.ll.medium_mission.member.entity.Member;
import com.ll.medium_mission.member.service.MemberService;
import com.ll.medium_mission.member.validator.JoinValidator;
import com.ll.medium_mission.member.validator.ModifyValidator;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final CookieProvider cookieProvider;

    private final ValidateUtil validateUtil;
    private final JoinValidator joinValidator;
    private final ModifyValidator modifyValidator;

    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody @Valid MemberJoinRequest memberJoinRequest, BindingResult bindingResult) {
        joinValidator.validate(memberJoinRequest, bindingResult);

        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        memberService.join(memberJoinRequest.email(), memberJoinRequest.nickname(), memberJoinRequest.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody @Valid MemberLoginRequest memberLoginRequest, BindingResult bindingResult) {
        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        MemberLoginResponse memberLoginResponse = memberService.login(memberLoginRequest.email(), memberLoginRequest.password());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, String.valueOf(
                        cookieProvider.createAccessTokenCookie(memberLoginResponse.accessToken()))
                )
                .header(HttpHeaders.SET_COOKIE, String.valueOf(
                        cookieProvider.createRefreshTokenCookie(memberLoginResponse.refreshToken()))
                )
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/logout")
    public ResponseEntity<?> logoutMember(Principal principal) {
        SecurityContextHolder.clearContext();
        memberService.logout(principal.getName());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, String.valueOf(
                        cookieProvider.removeToken("accessToken"))
                )
                .header(HttpHeaders.SET_COOKIE, String.valueOf(
                        cookieProvider.removeToken("refreshToken"))
                )
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public ResponseEntity<?> modifyMember(@RequestBody @Valid MemberModifyRequest memberModifyRequest, BindingResult bindingResult, Principal principal) {
        modifyValidator.validate(memberModifyRequest, bindingResult);

        if (validateUtil.hasErrors(bindingResult)) {
            return validateUtil.getErrors(bindingResult);
        }

        memberService.modify(principal.getName(), memberModifyRequest.nickname(), memberModifyRequest.password(), memberModifyRequest.newPassword());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/membership")
    public void registerMembership(Principal principal) {
        memberService.registerMembership(principal.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/membership")
    public void cancelMembership(Principal principal) {
        memberService.cancelMembership(principal.getName());
    }

    @GetMapping("/check")
    public MemberCheckResponse checkMember(Principal principal) {
        if (principal != null) {
            Member member = memberService.getMember(principal.getName());

            return MemberCheckResponse.builder()
                    .nickname(member.getNickname())
                    .isPaid(member.getIsPaid())
                    .result(true)
                    .build();
        }

        return MemberCheckResponse.builder()
                .result(false)
                .build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public MemberResponse getProfile(Principal principal) {
        return memberService.getMemberResponse(principal.getName());
    }
}
