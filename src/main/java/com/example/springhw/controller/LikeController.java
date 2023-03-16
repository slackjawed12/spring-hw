package com.example.springhw.controller;

import com.example.springhw.dto.LikeRequestDto;
import com.example.springhw.security.UserDetailsImpl;
import com.example.springhw.service.LikeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    /**
     * 게시글 좋아요
     */
    @ResponseBody
    @PostMapping("/api/posts/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.likePost(postId, userDetails.getMember());

        return ResponseEntity.ok("게시글 좋아요 등록");
    }
    /**
     * 게시글 좋아요
     */
    @ResponseBody
    @PostMapping("/api/posts/{postId}/like")
    public ResponseEntity likePost2(@PathVariable Long postId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.likePost(postId, userDetails.getMember());

        return ResponseEntity.ok("");
    }
    /**
     * 댓글 좋아요
     */
    @ResponseBody
    @PostMapping("/api/comment/{commentId}/like")
    public ResponseEntity<String> likeComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.likeComment(commentId, userDetails.getMember());
        return ResponseEntity.ok("댓글 좋아요 등록");
    }
}
