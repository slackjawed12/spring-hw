package com.example.springhw.service;

import com.example.springhw.dto.PostRequestDto;
import com.example.springhw.dto.PostResponseDto;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.MemberRoleEnum;
import com.example.springhw.entity.Posts;
import com.example.springhw.jwt.JwtUtil;
import com.example.springhw.repository.MemberRepository;
import com.example.springhw.repository.PostRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public List<Posts> getPostsInfo() {
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    // service 단에서 entity -> dto 변환함
    @Transactional
    public List<PostResponseDto> getAllPosts() {
        List<PostResponseDto> dtoList = new ArrayList<>();
        postRepository.findAllByOrderByModifiedAtDesc().forEach(x -> {
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
    public PostResponseDto createPost(PostRequestDto requestDto, Member member) {
        Posts post = new Posts(requestDto, member);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);   // 헤더에서 토큰 값 가져오기
        Claims claims;
        Posts post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("잘못된 url"));

        if (token != null) {    // token 값이 있음
            if (jwtUtil.validateToken(token)) { // 유효한 token
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("유효하지 않은 토큰");
            }

            // 토큰의 username db에 있는지 확인
            Member member = memberRepository.findByUsername(claims.getSubject())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자의 토큰"));

            // ADMIN 계정이거나, 멤버 id와 post entity의 멤버 id가 같으면 수정
            if (member.getRole() == MemberRoleEnum.ADMIN || member.getId().equals(post.getMember().getId())) {
                post.update(requestDto);
                return new PostResponseDto(post);
            } else {
                throw new IllegalArgumentException("수정 권한이 없습니다");
            }
        }
        throw new IllegalArgumentException("토큰 없음");
    }

    @Transactional
    public void delete(Long id, HttpServletRequest request) {
        log.info("request header={}", request.getHeader("Authorization"));
        String token = jwtUtil.resolveToken(request);   // 헤더에서 토큰 값 가져오기
        Claims claims;
        Posts post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("잘못된 url"));

        if (token != null) {    // token 값이 있음
            if (jwtUtil.validateToken(token)) { // 유효한 token
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("유효하지 않은 토큰");
            }

            // 토큰의 username db에 있는지 확인
            Member member = memberRepository.findByUsername(claims.getSubject())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자의 토큰"));

            // ADMIN 계정이거나, 멤버 id와 post entity의 멤버 id가 같으면 삭제
            if (member.getRole() == MemberRoleEnum.ADMIN || member.getId().equals(post.getMember().getId())) {
                postRepository.deleteById(id);
                return;
            } else {
                throw new IllegalArgumentException("삭제 권한이 없습니다");
            }
        }
        throw new IllegalArgumentException("토큰 없음");
    }
}
