package com.example.board.controller;

import java.net.Authenticator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    
    // private final

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin 폼 요청");
    }

    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 폼 요청");
    }
    
    @GetMapping("/register")
    public void getRegister() {

    }
    
    @GetMapping("/logout")
    public void getLogout() {

    }

    @ResponseBody
    @GetMapping("/auth")
    public Authentication gAuthentication () {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }

}
