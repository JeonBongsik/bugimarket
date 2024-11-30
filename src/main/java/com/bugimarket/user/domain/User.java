package com.bugimarket.user.domain;

import com.bugimarket.common.Track;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더용 생성자
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Long userId;

    @Column(unique = true, updatable = false, nullable = false)
    private String email;
    private String pwd;
    private String nickName;

    private Track firstTrack;
    private Track secondTrack;


    public void changeNickName(String nickName) {
        this.nickName = nickName;
    }


}