package com.example.springhw.service;

import com.example.springhw.dto.LoginRequestDto;
import com.example.springhw.dto.MemberResponseDto;
import com.example.springhw.dto.SignupRequestDto;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.MemberRoleEnum;
import com.example.springhw.jwt.JwtUtil;
import com.example.springhw.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    private static final String ADMIN_KEY = "woohaha";

    public MemberResponseDto findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("회원 상세 조회 실패"));
        return new MemberResponseDto(member);
    }

    public List<MemberResponseDto> findAllMember() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        boolean admin = requestDto.isAdmin();

        if (memberRepository.findByUsername(username).isEmpty()) {
            String email = username + "@myblog.com";    // 이메일 디폴트 값 설정
            MemberRoleEnum role = admin ? MemberRoleEnum.ADMIN : MemberRoleEnum.USER;
            if (role.equals(MemberRoleEnum.ADMIN)) {    // 관리자 회원 가입이면
                String adminKey = requestDto.getAdminKey();
                if (!adminKey.equals(ADMIN_KEY)) {  // 내장된 ADMIN_KEY, 요청 문자열 비교
                    throw new IllegalArgumentException("요청한 관리자 키가 틀렸습니다");
                }
            }
            Member member = new Member(username, password, email, role);
            memberRepository.save(member);
            return;
        }
        throw new IllegalArgumentException("중복된 사용자 존재");
    }

    @Transactional(readOnly = true)
    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        log.info("username={}", username);
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자 없음"));

        if(!member.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호 일치하지 않습니다");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member.getUsername(), member.getRole()));
    }
}
