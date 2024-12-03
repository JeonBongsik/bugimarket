package com.bugimarket.post.dto;
import com.bugimarket.common.Category;
import com.bugimarket.common.Location;
import com.bugimarket.common.Track;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    private String title; // 게시글 제목

    private String description; // 게시글 내용

    private String itemName; // 상품명

    private Integer price; // 상품 가격

    private Category category; // 카테고리

    private Track track; // 트랙 정보

    private Location location; // 거래 위치 장소

    private String locationDescription; // 거래 위치 설명 (예: OO 아파트 근처)

}
