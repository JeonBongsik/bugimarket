package com.bugimarket.post.dto;

import com.bugimarket.common.Category;
import com.bugimarket.common.Location;
import com.bugimarket.common.Status;
import com.bugimarket.common.Track;
import lombok.Builder;


@Builder
public class PostInfo {


    private Long sellerId; // 판매자 ID
    private String title; // 게시글 제목

    private String description; // 게시글 내용

    private String itemName; // 상품명

    private Integer price; // 상품 가격

    private Category category; // 카테고리

    private Track track; // 트랙 정보

    private Location location; // 거래 위치 장소

    private String locationDescription; // 거래 위치 설명 (예: OO 아파트 근처)

    private Status saleStatus; // 판매 상태 (판매중, 예약, 완료 등)

    // 이미지 리스트 (여러 이미지가 있을 경우를 고려)
    //private List<String> imageUrls; // 이미지 URL 리스트

}
