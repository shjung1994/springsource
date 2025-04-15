package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Log4j2 //log.info("home 요청"); 쓸 수 있게 해줌
@Controller
public class HomeController {

    // http://localhost:8080/
    @GetMapping("/")
    public String getHome() {
        log.info("home 요청");
        return "home"; // home은 template파일(resources아래) 이름(내 마음대로 정할 수 있음)
    }

    // http://localhost:8080/basic
    @GetMapping("/basic")
    public String getMethodName() {
        return "info";
    }

    @PostMapping("/basic")
    // new model 안해도 됨
    // int 'num'이부분 .html에서 'name'이랑 맞춰야함
    public String postAdd(@ModelAttribute("num1") int num1, @ModelAttribute("num2") int num2, Model model) {
        log.info("덧셈 요청 {},{}", num1, num2);
        // 덧셈 결과를 info로 전송
        int result = num1 + num2;
        // ("키(name, 중복만 안되면 됨)", value), name = 화면단에서 불러서 사용
        model.addAttribute("result", result);
        // model.addAttribute("num1", num1);
        // model.addAttribute("num2", num2);
        // '("result", result)에 담은 값을 같이 가지고 갑시다'라는 뜻
        return "info";
    }
    
}
