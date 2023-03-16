package com.example.springhw.repository;

import com.example.springhw.entity.Comment;
import com.example.springhw.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByCreatedAtDesc(Posts post);
    void deleteAllByPost(Posts post);
}
