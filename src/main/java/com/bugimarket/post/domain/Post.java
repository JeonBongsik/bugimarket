package com.bugimarket.post.domain;

import com.bugimarket.common.Category;
import com.bugimarket.common.Location;
import com.bugimarket.common.Status;
import com.bugimarket.common.Track;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더용 생성자
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false, nullable = false)
    private Long postId; // 게시글 고유 ID

    private Long sellerId; // 판매자 ID

    private String title; // 게시글 제목

    private String description; // 게시글 내용

    private String itemName; // 상품명

    private Integer price; // 상품 가격

    private Category category; // 카테고리

    private Track track; // 트랙 예시 (게임그래픽디자인트랙, 모바일소프트웨어트랙, 빅데이터트랙, 디지털콘텐츠가상현실트랙 등등)

    private Location location; // 거래 위치 장소 예시 (상상관,공학관,미래관,인성관 등등)

    private String locationDescription; // 거래 위치 설명 (예: OO 아파트 근처)

    private Status saleStatus; // 판매 상태 (판매중, 예약, 완료 등)



    // 이미지도 고려해야함

//    // 조회수, 좋아요 수 등 추후 고려
//    private Integer viewCount; // 조회수
//    private Integer likeCount; // 좋아요 수
//    private Boolean isViewed; // 조회 중복 체크
//
//    // 이미지 관련
//    private String imageUrl; // 대표 이미지 URL (추가적으로 List<String> 형태도 고려 가능)


    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeItemName(String itemName) {
        this.itemName = itemName;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changeTrack(Track track) {
        this.track = track;
    }

    public void changeLocation(Location location) {
        this.location = location;
    }

    public void changeLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public void changeSaleStatus(Status saleStatus) {
        this.saleStatus = saleStatus;
    }

}
