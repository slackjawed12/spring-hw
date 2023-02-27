package com.example.springhw.controller;

import com.example.springhw.dto.PostRequestDto;
import com.example.springhw.dto.PostResponseDto;
import com.example.springhw.entity.Posts;
import com.example.springhw.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 비밀번호, 날짜 잘 들어갔나 확인 용도
    @GetMapping("/admin")
    public List<Posts> getPostsInfo(){
        return postService.getPostsInfo();
    }

    // 요구사항 1. 전체 게시글 목록 조회 - 작성 날짜 기준 내림차순 정렬
    @GetMapping("/")
    @ResponseBody
    public List<PostResponseDto> showAll(){
        return postService.getAllPosts();
    }

    // 요구사항 2. 게시글 작성
    @PostMapping("/api/posts")
    public Posts createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 요구사항 3. 선택 게시글 조회
    @GetMapping("/api/posts/{id}")
    public PostResponseDto showPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    // 요구사항 4. 선택 게시글 수정 - 비밀번호 일치 시 수정
    @PutMapping("/api/posts/{id}")
    public Long updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    // 요구사항 5. 선택 게시글 삭제 - 비밀번호 일치 시 삭제
    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.delete(id, requestDto);
    }
}
