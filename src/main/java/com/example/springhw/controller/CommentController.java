package com.example.springhw.controller;

import com.example.springhw.dto.CommentRequestDto;
import com.example.springhw.dto.CommentResponseDto;
import com.example.springhw.security.UserDetailsImpl;
import com.example.springhw.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 작성
     */
    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<String> createComment(@PathVariable Long postId,
                                                      @RequestBody CommentRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.createComment(postId, requestDto, userDetails.getMember());
        return ResponseEntity.ok("댓글 등록 성공");
    }

    /**
     * 댓글 수정
     */
    @ResponseBody
    @PutMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId,
                                                            @PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.update(postId, commentId, requestDto, userDetails.getMember()));
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.delete(postId, commentId, userDetails.getMember());
        return ResponseEntity.ok("삭제 성공");
    }
}
