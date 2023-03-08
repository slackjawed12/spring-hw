package com.example.springhw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberFormController {

    /**
     * 회원가입 페이지
     */
    @GetMapping("/signup")
    public ModelAndView signupForm() {
        return new ModelAndView("signup");
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
