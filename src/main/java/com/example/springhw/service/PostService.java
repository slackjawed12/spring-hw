package com.example.springhw.service;

import com.example.springhw.dto.PostRequestDto;
import com.example.springhw.dto.PostResponseDto;
import com.example.springhw.entity.Member;
import com.example.springhw.entity.Posts;
import com.example.springhw.jwt.JwtUtil;
import com.example.springhw.repository.MemberRepository;
import com.example.springhw.repository.PostRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);   // 헤더에서 토큰 값 가져오기
        Claims claims;

        if (token != null) {    // token 값이 있음
            if (jwtUtil.validateToken(token)) { // 유효한 token
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return null;
            }

            // 토큰의 username db에 있는지 확인
            Optional<Member> member = memberRepository.findByUsername(claims.getSubject());
            if (member.isEmpty()) { // DB 사용자 없음
                log.info("존재하지 않는 사용자의 토큰");
                return null;
            }

            Posts post = new Posts(requestDto, member.get());
            postRepository.save(post);
            return new PostResponseDto(post);
        } else {
            return null;
        }
    }

    @Transactional
    public PostResponseDto update(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        log.info("service update param id={}", id);
        String token = jwtUtil.resolveToken(request);   // 헤더에서 토큰 값 가져오기
        Claims claims;
        Optional<Posts> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("잘못된 url");
        }

        if (token != null) {    // token 값이 있음
            if (jwtUtil.validateToken(token)) { // 유효한 token
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return null;
            }

            // 토큰의 username db에 있는지 확인
            Optional<Member> member = memberRepository.findByUsername(claims.getSubject());
            if (member.isEmpty()) { // DB 사용자 없음
                log.info("존재하지 않는 사용자의 토큰");
                return null;
            }

            Member m = member.get();
            Posts p = post.get();
            if (m.getId().equals(p.getMember().getId())) {  // 멤버 id와 post entity의 멤버 id가 같으면 수정
                p.update(requestDto);
                return new PostResponseDto(p);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Transactional
    public Long delete(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);   // 헤더에서 토큰 값 가져오기
        Claims claims;
        Optional<Posts> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new IllegalArgumentException("잘못된 url");
        }

        if (token != null) {    // token 값이 있음
            if (jwtUtil.validateToken(token)) { // 유효한 token
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                return null;
            }

            // 토큰의 username db에 있는지 확인
            Optional<Member> member = memberRepository.findByUsername(claims.getSubject());
            if (member.isEmpty()) { // DB 사용자 없음
                log.info("존재하지 않는 사용자의 토큰");
                return null;
            }

            Member m = member.get();
            Posts p = post.get();
            if (m.getId().equals(p.getMember().getId())) {  // 멤버 id와 post entity의 멤버 id가 같으면 삭제
                postRepository.deleteById(id);
                return id;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
