package com.example.movie.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movie.dto.MemberDTO;
import com.example.movie.dto.OAuthMemberDTO;
import com.example.movie.dto.PasswordDTO;
import com.example.movie.entity.MemberRole;
import com.example.movie.service.CustomMemberDetailsService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Log4j2
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final CustomMemberDetailsService service;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/leave")
    public void getLeave() {
        log.info("회원탈퇴 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/leave")
    public String postLeave(MemberDTO memberDTO, HttpSession session) {
        log.info("회원탈퇴 요청 {}", memberDTO);
        service.leaveMember(memberDTO);
        session.invalidate();
        return "redirect:/movie/list";
    }
    
    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 폼 요청");
    }
        
    @GetMapping("/register")
    public void getRegister(MemberDTO memberDTO) {
        log.info("회원가입 폼 요청");
    }
    
    @PostMapping("/register")
    public String postMethodName(@Valid MemberDTO memberDTO, BindingResult result, Model model) {
        log.info("회원가입 요청 {}", memberDTO);

        if (result.hasErrors()) {
            return "/member/register";
        }

        memberDTO.setMemberRole(MemberRole.MEMBER);
        
        try {
            service.register(memberDTO);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/member/register";
        }

        return "redirect:/member/login";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public void getProfile() {
        log.info("프로필 폼 요청");
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/nickname")
    public String postNickname(MemberDTO memberDTO, Principal principal) {
        log.info("닉네임 수정 요청 {}", memberDTO);

        // 이메일 설정
        memberDTO.setEmail(principal.getName());
        service.updateNickname(memberDTO);

        // SecurityContext 업데이트
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        OAuthMemberDTO oAuthMemberDTO = (OAuthMemberDTO)authentication.getPrincipal();
        oAuthMemberDTO.getMemberDTO().setNickname(memberDTO.getNickname());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/password")
    public String postPassword(PasswordDTO passwordDTO, Model model, HttpSession session) {
        log.info("비밀번호 수정 요청 {}", passwordDTO);

        try {
            service.updatePassword(passwordDTO);
        } catch (Exception e) {
            // 현재 비밀번호가 다를 때
            model.addAttribute("error", e.getMessage());
            return "/member/edit";
        }
        // 비밀번호 변경
        // 1) 로그인 페이지로 이동(세션 해제 후)
        session.invalidate();
        return "redirect:/member/login";
        // 2) SecurityContext 비밀번호 업데이트
        // return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit")
    public void getEdit() {
        log.info("프로필 수정 폼 요청");
    }
    
    // 개발자 확인용
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    @GetMapping("/auth")
    public Authentication gAuthentication () {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        return authentication;
    }

}
