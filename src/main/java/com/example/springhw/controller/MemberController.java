package com.example.springhw.controller;

import com.example.springhw.dto.LoginRequestDto;
import com.example.springhw.dto.MemberResponseDto;
import com.example.springhw.dto.SignupRequestDto;
import com.example.springhw.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 한 회원의 userId가 주었을때 회원 정보를 조회하는 API
     */
    @GetMapping("/{id}")
    public MemberResponseDto getMemberInfo(@PathVariable Long id) {
        return memberService.findMember(id);
    }

    /**
     * 회원 전체 목록
     */
    @GetMapping("/")
    @ResponseBody
    public List<MemberResponseDto> getMemberList() {
        return memberService.findAllMember();
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/signup")
    public ModelAndView signupForm() {
        return new ModelAndView("signup");
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * 회원 가입
     * request : html form tag - x-www-urlencoded (username, password)
     * response : message (String)
     */
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<String> signup(@ModelAttribute("requestDto") @Valid SignupRequestDto requestDto,
                                         HttpServletResponse response,
                                         BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.ofNullable(memberService.signup(requestDto, response));
    }

    /**
     * 로그인
     * request : application/json
     * response : message (String)
     * 클라이언트가 쿠키 저장, 리다이렉트 함
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto requestDto, BindingResult result,
                                        HttpServletResponse response) {
        log.info("username={}",requestDto.getUsername());
        log.info("password={}",requestDto.getPassword());
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldError().getDefaultMessage());
        }
        String message = memberService.login(requestDto, response);
        return ResponseEntity.ofNullable(message);
    }
}
