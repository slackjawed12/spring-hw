package com.example.springhw.service;

import com.example.springhw.dto.MemberResponseDto;
import com.example.springhw.dto.SignupRequestDto;
import com.example.springhw.entity.Member;
import com.example.springhw.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto findMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("회원 상세 조회 실패"));
        return new MemberResponseDto(member);
    }

    public List<MemberResponseDto> findAllMember() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::new).collect(Collectors.toList());
    }

    public boolean saveMember(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        if (memberRepository.findByUsername(username).isEmpty()) {
            String email = username + "@myblog.com";
            Member member = new Member(username, password, email);
            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
