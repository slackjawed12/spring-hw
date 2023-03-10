package com.example.springhw.controller;

import com.example.springhw.dto.PostResponseDto;
import com.example.springhw.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostFormController {
    private final PostService postService;

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
}
