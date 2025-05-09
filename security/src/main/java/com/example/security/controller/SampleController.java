package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@RequestMapping("/sample")
@Log4j2
@Controller
public class SampleController {

    @GetMapping("/guest")
    public void getGuest() {
        log.info("guest");
    }

    @GetMapping("/member")
    public void getMember() {
        log.info("member");
    }

    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin");
    }
}
