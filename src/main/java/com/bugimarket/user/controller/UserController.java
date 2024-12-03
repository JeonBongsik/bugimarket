package com.bugimarket.user.controller;
import com.bugimarket.user.dto.CreateUserRequest;
import com.bugimarket.user.dto.UpdateUserRequest;
import com.bugimarket.user.dto.UserInfo;
import com.bugimarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    // 유저 생성 (회원 가입)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createUser (@RequestPart CreateUserRequest createUserRequest,
                                         @RequestPart MultipartFile image) {
        try {
            userService.createUser(createUserRequest, image);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    // 유저 조회
    @GetMapping
    public ResponseEntity<UserInfo> getUserInfo(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    // 유저 수정
    @PutMapping
    public ResponseEntity updateUser(@AuthenticationPrincipal Long userId,
                                     @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUser(userId, updateUserRequest);
        return ResponseEntity.ok("");
    }


    // 유저 삭제
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal Long userId) {
                userService.deleteUser(userId);
                return ResponseEntity.ok("");
    }







}

