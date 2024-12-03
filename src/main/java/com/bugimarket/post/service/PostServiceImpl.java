package com.bugimarket.post.service;


import com.bugimarket.common.Status;
import com.bugimarket.post.domain.Post;
import com.bugimarket.post.dto.CreatePostRequest;
import com.bugimarket.post.dto.PostInfo;
import com.bugimarket.post.dto.UpdatePostRequest;
import com.bugimarket.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    // 게시글 생성
    @Override
    public void createPost(Long userId, CreatePostRequest createPostRequest, List<MultipartFile> images) {
        Post createdPost = Post.builder()
                .price(createPostRequest.getPrice())
                .category(createPostRequest.getCategory())
                .description(createPostRequest.getDescription())
                .title(createPostRequest.getTitle())
                .track(createPostRequest.getTrack())
                .itemName(createPostRequest.getItemName())
                .location(createPostRequest.getLocation())
                .locationDescription(createPostRequest.getLocationDescription())
                .saleStatus(Status.판매중)
                .build();

        //추후 이미지 S3에 올리고 여기에 추가해야한다.

        postRepository.save(createdPost);
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    @Override
    public PostInfo getPostInfo(Long postId) {

        Post post = postRepository.findById(postId).get();

        return PostInfo.builder()
                .sellerId(post.getSellerId())
                .price(post.getPrice())
                .category(post.getCategory())
                .description(post.getDescription())
                .itemName(post.getItemName())
                .location(post.getLocation())
                .locationDescription(post.getLocationDescription())
                .saleStatus(post.getSaleStatus())
                .title(post.getTitle())
                .track(post.getTrack())
                .build();
    }


    //게시글 수정
    @Override
    public void updatePost(Long userId, Long postId, UpdatePostRequest updatePostRequest) throws IllegalAccessException {
        // 판매자 아이디가 접속중인 아이디와 같은 지 확인한다. => 표시 해야할 듯

        Post post = postRepository.findById(postId).get();

        if(userId.equals(updatePostRequest.getSellerId())){
            post.changeCategory(updatePostRequest.getCategory());
            post.changeDescription(updatePostRequest.getDescription());
            post.changeItemName(updatePostRequest.getItemName());
            post.changeLocation(updatePostRequest.getLocation());
            post.changePrice(updatePostRequest.getPrice());
            post.changeLocationDescription(updatePostRequest.getLocationDescription());
            post.changeTitle(updatePostRequest.getTitle());
            post.changeTrack(updatePostRequest.getTrack());
            post.changeSaleStatus(updatePostRequest.getSaleStatus());
        }
        // 바꿀 권한이 없음.
        else{
            throw new IllegalAccessException("You do not have permission to update this post.");
        }

    }


    //게시글 삭제
    @Override
    public void deletePost(Long userId, Long postId) throws IllegalAccessException {
        // 관련된 거 고려해서 삭제해야한다. 예 이미지
        // 일단 간단하게 진행

        Post post = postRepository.findById(postId).get();
        if(userId.equals(post.getSellerId())){
              postRepository.delete(post);
        }
        // 바꿀 권한이 없음.
        else{
            throw new IllegalAccessException("You do not have permission to update this post.");
        }

    }


}
