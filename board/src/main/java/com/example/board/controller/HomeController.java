package com.example.board.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class HomeController {
    
    @GetMapping("/")
    public String getHome() {
        return "redirect:/board/list";
        // return "home";
    }

}
