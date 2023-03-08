package com.example.springhw.entity;

import com.example.springhw.dto.CommentRequestDto;
import com.example.springhw.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    // 단방향 연결 - post 삭제 시 cascade 사용하지 않을 것
    @ManyToOne
    @JoinColumn(name="POSTS_ID", nullable = false)
    private Posts post;

    public Comment(String contents, Member member, Posts post) {
        this.contents = contents;
        this.member = member;
        this.post = post;
    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
