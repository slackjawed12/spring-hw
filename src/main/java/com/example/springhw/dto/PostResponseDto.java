package com.example.springhw.dto;

import com.example.springhw.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String username;
    private String contents;
    @CreatedDate
    private LocalDateTime createdAt;

    public PostResponseDto(Posts post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getMember().getUsername();
        this.contents = post.getContents();
        this.createdAt = post.getCreatedAt();
    }
}
