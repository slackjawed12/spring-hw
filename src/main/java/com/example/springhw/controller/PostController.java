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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

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
     * 수정 폼
     */
    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        PostResponseDto post = postService.getPost(id);
        model.addAttribute("post", post);
        return "editPost";
    }

    /**
     * 게시글 작성
     * response : 작성 후 해당 게시글 상세보기 페이지로 리다이렉트
     */
    @PostMapping
    public String createPost(@RequestBody PostRequestDto requestDto,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request) {
        PostResponseDto post = postService.createPost(requestDto, request);
        redirectAttributes.addAttribute("postId", post.getId());
        return "redirect:/api/posts/{postId}";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        PostResponseDto post = postService.getPost(id);
        model.addAttribute("post", post);
        return "post";
    }

    @ResponseBody
    @PutMapping("/{id}")
    public String updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto,
                             HttpServletRequest request) {
        log.info("updatePost param id={}", id);
        PostResponseDto post = postService.update(id, requestDto, request);
        if (post != null) {
            return "success";
        } else {
            return "fail";
        }
    }

    @DeleteMapping("/{id}")
    public Long deletePost(@PathVariable Long id, HttpServletRequest request) {
        return postService.delete(id, request);
    }
}
