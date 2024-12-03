package com.bugimarket.user.service;

import com.bugimarket.user.domain.User;
import com.bugimarket.user.dto.CreateUserRequest;
import com.bugimarket.user.dto.UpdateUserRequest;
import com.bugimarket.user.dto.UserInfo;
import com.bugimarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 유저 생성 (회원가입)
    @Override
    public void createUser(CreateUserRequest createUserRequest, MultipartFile image) {

        User user = userRepository.findByEmail(createUserRequest.getEmail());

        if(user != null){
        // 이미 존재하는 이메일입니다.
        }
        else{
            User createdUser = User.builder().
                    email(createUserRequest.getEmail()).
                    pwd(bCryptPasswordEncoder.encode(createUserRequest.getPwd())).
                    nickName(createUserRequest.getNickName()).
                    firstTrack(createUserRequest.getFirstTrack()).
                    secondTrack(createUserRequest.getSecondTrack()).
                    build();
            userRepository.save(createdUser);
        }
    }


    //유저 조회
    @Override
    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Long userId) {
        User user = userRepository.findById(userId).get();
        return UserInfo.builder()
                .firstTrack(user.getFirstTrack())
                .secondTrack(user.getSecondTrack())
                .nickName(user.getNickName())
                .build();
    }

    //유저 수정
    @Override
    public void updateUser(Long userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId).get();
        user.changeNickName(updateUserRequest.getNickName());
        user.changeFirstTrack(updateUserRequest.getFirstTrack());
        user.changeSecondTrack(updateUserRequest.getSecondTrack());
    }


    //유저 삭제
    @Override
    public void deleteUser(Long userId) {
        //1. 신경 써야할 부분 S3 이미지 삭제, 관련된 게시글 모두 삭제
        //2. 일단 유저만
        User user = userRepository.findById(userId).get();
        userRepository.delete(user);
    }







    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
