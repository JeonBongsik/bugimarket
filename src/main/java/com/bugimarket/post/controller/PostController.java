package com.bugimarket.post.controller;

import com.bugimarket.user.dto.CreateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    @GetMapping
    public ResponseEntity<String> createUser (@AuthenticationPrincipal Object principal) {


        if (principal instanceof Long) {
            Long userId = (Long) principal;
            return ResponseEntity.ok("User profile for userId: " + userId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user");
        }


    }

}
