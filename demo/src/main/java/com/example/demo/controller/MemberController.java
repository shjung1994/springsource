package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;


@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

    // http://localhost:8080/member/register
    // void: templates/member/register.html

    // 회원가입: /member/register
    @GetMapping("/register")
    public void getRegister(@ModelAttribute("mDTO") MemberDTO memberDTO) {
        // template 찾는 것
        log.info("회원가입");
    }

    @PostMapping("/register")
    // @ModelAttribute("mDTO") => mDTO라는 별칭을 붙인 것, 별칭을 붙이면 memberDTO로는 접근 못함
    public String postRegister(@ModelAttribute("mDTO") @Valid MemberDTO memberDTO, BindingResult result, RedirectAttributes rttr) {
        log.info("회원가입 요청 {}", memberDTO);
    
        // 유효성 검사 통과하지 못한다면 원래 입력 페이지로 돌아가기
        if (result.hasErrors()) {
            return "/member/register";
        }


        // 로그인 페이지로 이동(redirect: 다시 경로 요청)
        // return "redirect:/member/login";

        // redirect 방식으로 가면서 값을 보내고 싶다면?
        rttr.addAttribute("userid", memberDTO.getUserid()); // http://localhost:8080/member/login?userid=123
        rttr.addFlashAttribute("password", memberDTO.getPassword()); // http://localhost:8080/member/login;jsessionid=AB4BD021D6D969A8A0E08B11A0E6C5D4
        return "redirect:/member/login";

    }

    // 로그인: /member/login
    @GetMapping("/login")
    public void getLogin() {
        log.info("로그인 페이지 요청");
    }

    @PostMapping("/login")
    // public void postLogin(LoginDTO loginDTO) {
        // log.info("로그인 요청 {}, {}", loginDTO.getUserid(), loginDTO.getPassword());
        public void postLogin(HttpServletRequest request) {
            // HttpServletRequest : 사용자의 정보 및 서버 쪽 정보 추출용
            
// spring(login.html)을 안 썻다면 무조건 이 방식대로해야 함. 잘안씀. 불편...spring으로 하는게 좋음
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String remote = request.getRemoteAddr();
        String local = request.getLocalAddr();
        
        log.info("로그인 요청 {}, {}", userid, password);
        log.info("클라이언트 정보 {}, {}", remote, local);
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
