package com.example.springhw.service;

import com.example.springhw.dto.PostDeleteDto;
import com.example.springhw.dto.PostRequestDto;
import com.example.springhw.dto.PostResponseDto;
import com.example.springhw.entity.Posts;
import com.example.springhw.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public List<Posts> getPostsInfo() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    // service 단에서 entity -> dto 변환함
    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<PostResponseDto> dtoList = new ArrayList<>();
        postRepository.findAllByOrderByModifiedAtDesc().forEach(x->{
            PostResponseDto dto = new PostResponseDto(x);
            dtoList.add(dto);
        });

        return dtoList;
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Posts post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("아이디가 존재하지 않습니다."));
        return new PostResponseDto(post);
    }

    @Transactional
    public Posts createPost(PostRequestDto requestDto) {
        Posts post = new Posts(requestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Long update(Long id, PostRequestDto requestDto) {
        Posts posts = postRepository.findFirstByIdAndPassword(id, requestDto.getPassword()).orElseThrow(() ->
                new IllegalArgumentException("아이디나 비밀번호가 일치하지 않습니다"));
        posts.update(requestDto);

        return id;
    }

    @Transactional
    public Long delete(Long id, PostDeleteDto deleteDto) {
        postRepository.deleteByIdAndPassword(id, deleteDto.getPassword());
        return 0L;
    }
}
