package com.example.springhw.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String writer;
    private String contents;
    private String password;
}
