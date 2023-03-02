package com.example.springhw.assignment;

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
}
