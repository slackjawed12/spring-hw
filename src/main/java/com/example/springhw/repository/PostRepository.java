package com.example.springhw.repository;

import com.example.springhw.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Posts, Long> {
    List<Posts> findAllByOrderByModifiedAtDesc();
    Optional<Posts> findFirstByIdAndPassword(Long id, String password);
    void deleteByIdAndPassword(Long id, String password);
}
