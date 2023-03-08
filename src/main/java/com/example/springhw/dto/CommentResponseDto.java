package com.example.springhw.dto;

import com.example.springhw.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// 코멘트 가져오기 : 게시글 상세보기 페이지
@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;  // 수정 삭제 시 필요
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.username = comment.getMember().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
