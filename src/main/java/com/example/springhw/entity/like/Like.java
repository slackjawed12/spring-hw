package com.example.springhw.entity.like;

import jakarta.persistence.*;

public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    // getAllPost 할 때 like 개수 가져오기??
    @Column(nullable = false)
    LikeTypeEnum likeType;

    @Column(nullable = false)
    Long memberId;
}
