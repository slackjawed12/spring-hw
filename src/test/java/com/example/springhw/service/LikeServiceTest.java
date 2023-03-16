package com.example.springhw.service;

import com.example.springhw.dto.LikeRequestDto;
import com.example.springhw.dto.PostRequestDto;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.MemberRoleEnum;
import com.example.springhw.entity.Posts;
import com.example.springhw.entity.like.PostLike;
import com.example.springhw.repository.PostLikesRepository;
import com.example.springhw.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {
    @InjectMocks
    private LikeService likeService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostLikesRepository postLikesRepository;

    @Mock
    Member member;

    @Mock
    Posts post;

    @Test
    void testLikePost() {
        // given
        Long postId = 10L;
        LikeRequestDto requestDto = new LikeRequestDto(false);
        PostLike postLike = new PostLike(post, member);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // when, then
        Assertions.assertDoesNotThrow(() -> {
            likeService.likePost(postId, requestDto, member);
        });
    }

    @Test
    void likeComment() {
    }
}