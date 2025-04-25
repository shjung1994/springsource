package com.example.book.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Log4j2
public class HomeController {
    
    @GetMapping("/")
    public String getName() {
        return "redirect:/book/list";
        // return "home";
    }

}
