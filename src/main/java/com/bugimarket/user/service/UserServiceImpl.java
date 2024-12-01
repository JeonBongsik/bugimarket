package com.bugimarket.user.service;

import com.bugimarket.user.domain.User;
import com.bugimarket.user.dto.CreateUserRequest;
import com.bugimarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @Override
    public User findByEmail(String email) {
       return userRepository.findByEmail(email);
    }


}
