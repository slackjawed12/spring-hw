package com.example.springhw.dto;

import com.example.springhw.entity.Member;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z\\d]{4,10}$", message = "유효하지 않은 아이디 형식")
    private String username;

    @Pattern(regexp = "^[`~!@#$%^&*)(}{\\]\\[,./?><;':\\-_+=\\w]{8,15}$", message = "유효하지 않은 비밀번호 형식")
    private String password;

    private boolean admin;

    private String adminKey = "";
}
