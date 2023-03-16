package com.example.springhw.repository;

import com.example.springhw.entity.Comment;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.Posts;
import com.example.springhw.entity.like.CommentLike;
import com.example.springhw.entity.like.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentAndMember(Comment comment, Member member);
}
