package com.bugimarket.security.provider;


import com.bugimarket.user.domain.User;
import com.bugimarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName(); // 입력받은 이메일
        String password = (String) authentication.getCredentials(); // 입력받은 비밀번호


        // 사용자 정보 로드
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new BadCredentialsException("Invalid email");
        }

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, user.getPwd())) {
            throw new BadCredentialsException("Invalid password");
        }

        // 인증 성공 시 Authentication 반환
        return new UsernamePasswordAuthenticationToken(
                user, // 인증된 사용자 정보
                null, // 비밀번호는 null로 설정
                new ArrayList<>() // 권한 정보
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}