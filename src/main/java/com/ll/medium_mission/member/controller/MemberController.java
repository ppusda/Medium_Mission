package com.ll.medium_mission.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @PostMapping("/join")
    public void joinMember() {

    }

    @PostMapping("/login")
    public void loginMember() {

    }

    @PostMapping("/login")
    public void logoutMember() {

    }
}
