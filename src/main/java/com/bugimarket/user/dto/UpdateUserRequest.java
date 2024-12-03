package com.bugimarket.user.dto;

import com.bugimarket.common.Track;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String nickName;
    private Track firstTrack;
    private Track secondTrack;
}
