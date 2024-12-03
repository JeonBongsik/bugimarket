package com.bugimarket.user.dto;


import com.bugimarket.common.Track;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInfo {

    private String nickName;
    private Track firstTrack;
    private Track secondTrack;


}
