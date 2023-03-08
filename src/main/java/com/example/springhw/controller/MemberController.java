package com.example.springhw.controller;

import com.example.springhw.dto.LoginRequestDto;
import com.example.springhw.dto.MemberResponseDto;
import com.example.springhw.dto.SignupRequestDto;
import com.example.springhw.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * userId 바탕으로 회원 정보 조회하는 API
     */
    @GetMapping("/{id}")
    public MemberResponseDto getMemberInfo(@PathVariable Long id) {
        return memberService.findMember(id);
    }

    /**
     * 회원 전체 목록
     */
    @GetMapping
    @ResponseBody
    public List<MemberResponseDto> getMemberList() {
        return memberService.findAllMember();
    }

    /**
     * 회원 가입 API
     * request : html form tag - x-www-urlencoded (username, password)
     * response : 로그인 페이지로 리다이렉트
     */
    @PostMapping("/signup")
    public String signup(@ModelAttribute("requestDto") @Valid SignupRequestDto requestDto,
                         HttpServletResponse response) {
        memberService.signup(requestDto, response);
        return "redirect:/member/login";
    }

    /**
     * 로그인 API
     * request : application/json
     * response : message (String)
     * 클라이언트가 쿠키 저장, 리다이렉트 함
     */
    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody @Valid LoginRequestDto requestDto,
                                   HttpServletResponse response) {
        memberService.login(requestDto, response);
        return "success";
    }
}
