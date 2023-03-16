package com.example.springhw.service;

import com.example.springhw.dto.LikeRequestDto;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.Posts;
import com.example.springhw.entity.like.PostLike;
import com.example.springhw.repository.PostLikesRepository;
import com.example.springhw.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;

    public void likePost(Long postId, LikeRequestDto requestDto, Member member) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글"));
        if (requestDto.getLiked()) {  // 좋아요를 눌렀던 게시글
            postLikesRepository.deleteByPostAndMember(post, member);
        } else {
            postLikesRepository.save(new PostLike(post, member));
        }
    }

    public void likeComment(Map<String, String> idMap, LikeRequestDto requestDto, Member member) {

    }
}
