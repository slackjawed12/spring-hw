package com.example.springhw.dto;

import com.example.springhw.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;
    private String pw;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getUsername();
        this.email = member.getEmail();
        this.pw = member.getPassword();
    }
}
