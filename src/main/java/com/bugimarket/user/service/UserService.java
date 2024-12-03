package com.bugimarket.user.service;

import com.bugimarket.user.domain.User;
import com.bugimarket.user.dto.CreateUserRequest;
import com.bugimarket.user.dto.UpdateUserRequest;
import com.bugimarket.user.dto.UserInfo;
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

    /**
     * 이메일로 유저 찾기
     * @param email
     * @return User
     */

    User findByEmail(String email);

    /**
     * 유저 정보 조회
     * @param  userId
     * @return UserInfo
     */

    UserInfo getUserInfo(Long userId);


    /**
     * 유저 정보 변경
     * @param  userId,updateUserRequest
     * @return
     */

    void updateUser(Long userId, UpdateUserRequest updateUserRequest);


    /**
     * 유저 삭제
     * @param  userId,updateUserRequest
     * @return
     */

    void deleteUser(Long userId);
}
