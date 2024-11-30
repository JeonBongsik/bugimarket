package com.bugimarket.user.service;

import com.bugimarket.user.dto.CreateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {


    /**
     * 회원가입
     * @param createUserRequest 유저 정보 (email, pwd, NickName, firstTrack, secondTrack)
     * @param image 이미지
     * @return
     */
    public void createUser (CreateUserRequest createUserRequest,
                            MultipartFile image);



}
