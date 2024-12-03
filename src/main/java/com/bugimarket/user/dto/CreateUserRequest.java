package com.bugimarket.user.dto;


import com.bugimarket.common.Track;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String email;
    private String pwd;
    private String nickName;

    private Track firstTrack;
    private Track secondTrack;



}
