package com.example.springhw.service;

import com.example.springhw.dto.LoginRequestDto;
import com.example.springhw.dto.MemberResponseDto;
import com.example.springhw.dto.SignupRequestDto;
import com.example.springhw.entity.Member;
import com.example.springhw.jwt.JwtUtil;
import com.example.springhw.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public MemberResponseDto findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("회원 상세 조회 실패"));
        return new MemberResponseDto(member);
    }

    public List<MemberResponseDto> findAllMember() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new).collect(Collectors.toList());
    }

    public MemberResponseDto signup(SignupRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        if (memberRepository.findByUsername(username).isEmpty()) {
            String email = username + "@myblog.com";    // 이메일 디폴트 값 설정
            Member member = new Member(username, password, email);
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(username));
            memberRepository.save(member);
            return new MemberResponseDto(member);
        }
        return null;
    }

    public String login(LoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        Optional<Member> member = memberRepository.findByUsername(username);
        if (!member.isEmpty()) {
            Member m = member.get();
            if (m.getPassword().equals(password)) { // 로그인 성공
                // Authorization header에 토큰 내용 추가
                response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(username));
                return "success";
            } else {    // 비밀번호 틀림
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return null;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}
