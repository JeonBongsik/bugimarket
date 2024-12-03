package com.bugimarket.post.controller;

import com.bugimarket.post.dto.CreatePostRequest;
import com.bugimarket.post.dto.PostInfo;
import com.bugimarket.post.dto.UpdatePostRequest;
import com.bugimarket.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createPost(@AuthenticationPrincipal Long userId,
                                             @RequestPart CreatePostRequest createPostRequest,
                                             @RequestPart(required = false) List<MultipartFile> images) {
        try {
            postService.createPost(userId, createPostRequest, images);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    // 게시글 조회 *** 이거 생각 할 로직 **** // 아마 한번에 검색에서 다 뿌릴 거 같은데??
    @GetMapping("/{postId}")
    public ResponseEntity<PostInfo> getPostInfo(@PathVariable Long postId) {
            return ResponseEntity.ok(postService.getPostInfo(postId));
    }



    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@AuthenticationPrincipal Long userId,
                                             @PathVariable Long postId,
                                             @RequestBody UpdatePostRequest updatePostRequest) {
        try {
            postService.updatePost(userId, postId, updatePostRequest);
            return ResponseEntity.ok("Post updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@AuthenticationPrincipal Long userId,
                                             @PathVariable Long postId) {
        try {
            postService.deletePost(userId, postId);
            return ResponseEntity.ok("Post deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}
