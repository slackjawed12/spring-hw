package com.example.springhw.dto;

import com.example.springhw.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String writer;
    private String contents;
    @CreatedDate
    private LocalDateTime createdAt;

    public PostResponseDto(Posts post){
        this.title=post.getTitle();
        this.writer= post.getWriter();
        this.contents= post.getContents();
        this.createdAt=post.getCreatedAt();
    }
}
