package com.example.springhw.controller;

import com.example.springhw.dto.CommentResponseDto;
import com.example.springhw.dto.PostRequestDto;
import com.example.springhw.dto.PostResponseDto;
import com.example.springhw.service.CommentService;
import com.example.springhw.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    /**
     * 게시글 작성
     * response : 작성 후 해당 게시글 상세보기 페이지로 리다이렉트
     */
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto requestDto,
                                                      HttpServletRequest request) {
        return ResponseEntity.ok(postService.createPost(requestDto, request));
    }

    /**
     * 게시글 작성 - redirect
     * response : 작성 후 해당 게시글 상세보기 페이지로 리다이렉트
     */
//    @PostMapping
    public String createPost2(@RequestBody PostRequestDto requestDto,
                              HttpServletRequest request) {
        PostResponseDto post = postService.createPost(requestDto, request);
        // redirectAttributes.addAttribute("postId", post.getId());
        return "redirect:/api/posts/"+post.getId();
    }

    /**
     * 전체 게시글 목록 조회 - 작성 날짜 기준 내림차순 정렬
     */
    @GetMapping
    public String showAll(Model model) {
        List<PostResponseDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    /**
     * 특정 게시글 조회 - 게시글 id
     * post - 게시글
     * comments - 게시글 댓글
     */
    @GetMapping("/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        PostResponseDto post = postService.getPost(id);
        List<CommentResponseDto> comments = commentService.getComments(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);   // 해당 게시글 댓글 목록
        return "post";
    }

    /**
     * 게시글 수정
     */
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto,
                                                      HttpServletRequest request) {
        return ResponseEntity.ok(postService.update(id, requestDto, request));
    }

    /**
     * 게시글 삭제
     */
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest request) {
        commentService.delete(id, request); // 게시글의 댓글 삭제
        postService.delete(id, request);
        return ResponseEntity.ok("success");
    }
}
