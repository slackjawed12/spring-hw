package com.example.springhw.service;

import com.example.springhw.dto.CommentRequestDto;
import com.example.springhw.dto.CommentResponseDto;
import com.example.springhw.entity.Comment;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.MemberRoleEnum;
import com.example.springhw.entity.Posts;
import com.example.springhw.jwt.JwtUtil;
import com.example.springhw.repository.CommentRepository;
import com.example.springhw.repository.MemberRepository;
import com.example.springhw.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 게시글 id 값 받아서 comment list 반환
    @Transactional
    public List<CommentResponseDto> getComments(Long postId) {
        Posts post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments.stream().map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // comment 생성
    @Transactional
    public void createComment(Long postId, CommentRequestDto requestDto, Member member) {
        Posts post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글 ID가 유효하지 않습니다."));

        Comment comment = new Comment(requestDto.getContents(), member, post);
        commentRepository.save(comment);
    }

    /**
     * comment 수정
     */
    @Transactional
    public CommentResponseDto update(Long postId, Long commentId,
                                     CommentRequestDto requestDto, Member member) {
        // 존재하는 postId인지 확인
        Posts post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if (member.getRole() == MemberRoleEnum.ADMIN || member.getId().equals(post.getMember().getId())) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                    new IllegalArgumentException("존재하지 않는 Comment ID"));
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else {
            throw new IllegalArgumentException("수정 권한 없음");
        }
    }

    /**
     * 특정 게시글의 comment 모두 삭제 - 게시글 삭제 시 호출
     */
    @Transactional
    public void delete(Long postId, Member member) {
        Posts post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // ADMIN 계정이거나, 멤버 id와 post entity의 멤버 id가 같으면 댓글 삭제
        if (member.getRole() == MemberRoleEnum.ADMIN || member.getId().equals(post.getMember().getId())) {
            commentRepository.deleteAllByPost(post);
        } else {
            throw new IllegalArgumentException("삭제 권한 없음");
        }
    }

    /**
     * 특정 게시글의 특정 comment 삭제
     */
    @Transactional
    public void delete(Long postId, Long commentId, Member member) {
        Posts post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다."));
        // ADMIN 계정이거나, 멤버 id와 post entity의 멤버 id가 같으면 삭제
        if (member.getRole() == MemberRoleEnum.ADMIN || member.getId().equals(post.getMember().getId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException("삭제 권한 없음");
        }
    }
}
