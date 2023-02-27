package com.example.springhw.entity;

import com.example.springhw.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Posts extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String password;

    public Posts(PostRequestDto requestDto) {
        this.title=requestDto.getTitle();
        this.writer=requestDto.getWriter();
        this.contents= requestDto.getContents();
        this.password=requestDto.getPassword();
    }

    public void update(PostRequestDto requestDto) {
        this.title= requestDto.getTitle();
        this.writer= requestDto.getWriter();
        this.contents= requestDto.getContents();
    }
}
