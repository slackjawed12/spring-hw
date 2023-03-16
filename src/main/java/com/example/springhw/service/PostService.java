package com.example.springhw.service;

import com.example.springhw.dto.PostRequestDto;
import com.example.springhw.dto.PostResponseDto;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.MemberRoleEnum;
import com.example.springhw.entity.Posts;
import com.example.springhw.jwt.JwtUtil;
import com.example.springhw.repository.MemberRepository;
import com.example.springhw.repository.PostLikesRepository;
import com.example.springhw.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;

    @Transactional
    public List<Posts> getPostsInfo() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    // service 에서 entity -> dto 변환함
    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<PostResponseDto> dtoList = new ArrayList<>();
        postRepository.findAllByOrderByModifiedAtDesc().forEach(x -> {
            Long count = postLikesRepository.countByPost(x);    // 게시글 좋아요 개수
            PostResponseDto dto = new PostResponseDto(x, count);    // dto 변환
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
    public PostResponseDto createPost(PostRequestDto requestDto, Member member) {
        Posts post = new Posts(requestDto, member);
        postRepository.save(post);
        return new PostResponseDto(post, 0L);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, Member member) {
        Posts post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id값을 가진 게시글이 없습니다"));

        // 현재 로그인 한 사용자가 ADMIN 계정이거나, 멤버 id와 post entity의 멤버 id가 같으면 수정
        if (member.getRole() == MemberRoleEnum.ADMIN || member.getId().equals(post.getMember().getId())) {
            post.update(requestDto);
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("수정 권한이 없습니다");
        }
    }

    @Transactional
    public void delete(Long id, Member member) {
        Posts post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 id값을 가진 게시글이 없습니다"));

        // ADMIN 계정이거나, 멤버 id와 post entity의 멤버 id가 같으면 삭제
        if (member.getRole() == MemberRoleEnum.ADMIN || member.getId().equals(post.getMember().getId())) {
            postRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("삭제 권한이 없습니다");
        }
    }
}
