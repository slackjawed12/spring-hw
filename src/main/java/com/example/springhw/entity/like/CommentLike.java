package com.example.springhw.entity.like;

import com.example.springhw.entity.Comment;
import com.example.springhw.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    @ManyToOne
    Comment comment;

    @Column(nullable = false)
    @ManyToOne
    Member member;

    public CommentLike(Comment comment, Member member) {
        this.comment = comment;
        this.member = member;
    }
}
