package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

    // http://localhost:8080/member/register
    // void: templates/member/register.html

    // 회원가입: /member/register
    @GetMapping("/register")
    public void getRegister() {
        log.info("회원가입");
    }
    
    // 로그인: /member/login
    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인");
    }

    // 로그아웃: /member/logout
    @GetMapping("/logout")
    public void getLogout() {
        log.info("로그아웃");
    }

    // 비밀번호 변경: /member/change
    @GetMapping("/change")
    public void getChange() {
        log.info("비밀번호 변경");
    }
}
