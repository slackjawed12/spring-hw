package com.example.springhw.controller;

import com.example.springhw.dto.LoginRequestDto;
import com.example.springhw.dto.MemberResponseDto;
import com.example.springhw.dto.SignupRequestDto;
import com.example.springhw.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 한 회원의 userId가 주었을때 회원 정보를 조회하는 API
     *
     * @param id
     */
    @GetMapping("/{id}")
    public MemberResponseDto getMemberInfo(@PathVariable Long id) {
        return memberService.findMember(id);
    }

    /**
     * 회원의 전체 목록을 조회하는 API
     */
    @GetMapping("/")
    public List<MemberResponseDto> getMemberList() {
        return memberService.findAllMember();
    }

    /**
     * 회원 가입
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.ofNullable(memberService.saveMember(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequestDto requestDto, BindingResult result,
                                        HttpServletResponse response) {
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getFieldError().getDefaultMessage());
        }
        return ResponseEntity.ofNullable(memberService.login(requestDto, response));
    }
}
