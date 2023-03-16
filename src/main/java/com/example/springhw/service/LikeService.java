package com.example.springhw.service;

import com.example.springhw.dto.LikeRequestDto;
import com.example.springhw.entity.Comment;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.Posts;
import com.example.springhw.entity.like.CommentLike;
import com.example.springhw.entity.like.PostLike;
import com.example.springhw.repository.CommentLikesRepository;
import com.example.springhw.repository.CommentRepository;
import com.example.springhw.repository.PostLikesRepository;
import com.example.springhw.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final CommentRepository commentRepository;

    public void likePost(Long postId, Member member) {
        Posts post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("없는 게시글"));
        Optional<PostLike> postLike = postLikesRepository.findByPostAndMember(post, member);
        if (postLike.isPresent()) {  // 좋아요를 눌렀던 게시글
            postLikesRepository.deleteById(postLike.get().getId());
        } else {    // 좋아요 누르지 않았음
            postLikesRepository.save(new PostLike(post, member));
        }
    }

    public void likeComment(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("없는 댓글"));
        Optional<CommentLike> commentLike = commentLikesRepository.findByCommentAndMember(comment, member);
        if (commentLike.isPresent()) {  // 좋아요를 눌렀던 게시글
            commentLikesRepository.deleteById(commentLike.get().getId());
        } else {    // 좋아요 누르지 않았음
            commentLikesRepository.save(new CommentLike(comment, member));
        }
    }
}
