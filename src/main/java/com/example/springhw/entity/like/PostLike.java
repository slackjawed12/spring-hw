package com.example.springhw.entity.like;

import com.example.springhw.entity.Member;
import com.example.springhw.entity.Posts;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Member member;

    @ManyToOne
    Posts post;

    public PostLike(Posts post, Member member) {
        this.member = member;
        this.post = post;
    }
}
