package com.bugimarket.post.service;


import com.bugimarket.post.dto.CreatePostRequest;
import com.bugimarket.post.dto.PostInfo;
import com.bugimarket.post.dto.UpdatePostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {

    /**
     * 게시글 생성
     * @param userId,createPostRequest,images
     * @return
     */
    void createPost(Long userId, CreatePostRequest createPostRequest, List<MultipartFile> images);


    /**
     * 게시글 단건 조회
     * @param postId
     * @return PostInfo postinfo
     */

    PostInfo getPostInfo(Long postId);



    /**
     * 게시글 수정
     * @param userId,createPostRequest,images
     * @return
     */
    void updatePost(Long userId, Long postId, UpdatePostRequest updatePostRequest) throws IllegalAccessException;


    /**
     * 게시글 삭제
     * @param userId,postId
     * @return
     */
    void deletePost(Long userId, Long postId) throws IllegalAccessException;
}
