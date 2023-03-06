package com.example.springhw.assignment;

import com.example.springhw.dto.MemberResponseDto;
import com.example.springhw.dto.SignupRequestDto;
import com.example.springhw.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    @ResponseBody
    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto requestDto, HttpServletResponse response) throws ServletException {
        String usernamePattern = "^[a-z\\d]{4,10}$";
        String passwordPattern = "^\\w{8,15}$";
        if (!requestDto.getUsername().matches(usernamePattern)) {
            return "유효하지 않은 아이디 형식";
        } else if (!requestDto.getPassword().matches(passwordPattern)) {
            return "유효하지 않은 비밀번호 형식";
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
            return memberService.saveMember(requestDto) ? "회원 가입 성공" : "중복된 아이디";
        }
    }
}
