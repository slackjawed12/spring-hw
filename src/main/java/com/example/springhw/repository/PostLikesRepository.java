package com.example.springhw.repository;

import com.example.springhw.entity.Member;
import com.example.springhw.entity.like.PostLike;
import com.example.springhw.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikesRepository extends JpaRepository<PostLike, Long> {
    List<PostLike> findAllByPost(Posts post);

    void deleteByPostAndMember(Posts post, Member member);

    Long countByPost(Posts post);
}
