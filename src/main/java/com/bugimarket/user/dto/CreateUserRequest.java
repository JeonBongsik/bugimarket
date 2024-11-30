package com.bugimarket.user.dto;


import com.bugimarket.common.Track;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter

public class CreateUserRequest {

    private String email;
    private String pwd;
    private String nickName;

    private Track firstTrack;
    private Track secondTrack;



}
