package com.example.springhw.controller;

import com.example.springhw.dto.PostRequestDto;
import com.example.springhw.dto.PostResponseDto;
import com.example.springhw.entity.Posts;
import com.example.springhw.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    // 비밀번호, 날짜 잘 들어갔나 확인 용도
    @GetMapping("/admin")
    public List<Posts> getPostsInfo() {
        return postService.getPostsInfo();
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
     * 작성 폼
     */
    @GetMapping("/new")
    public String addPostForm() {
        return "addPost";
    }

    /**
     * 게시글 작성
     */
    @PostMapping
    public String createPost(@RequestBody PostRequestDto requestDto,
                             HttpServletRequest request) {
        log.info("title={}", requestDto.getTitle());
        log.info("contents={}", requestDto.getContents());
        postService.createPost(requestDto, request);
        return "redirect:/api/posts";
    }

    @GetMapping("/{id}")
    public PostResponseDto showPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto,
                           HttpServletRequest request) {
        return postService.update(id, requestDto, request);
    }

    @DeleteMapping("/{id}")
    public Long deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.delete(id, request);
    }
}
