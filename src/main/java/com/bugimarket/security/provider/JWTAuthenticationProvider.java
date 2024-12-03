package com.bugimarket.security.provider;

import com.bugimarket.security.dto.JWTAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getPrincipal();

        // JWT 토큰의 유효성 검증
        if (!jwtTokenProvider.validateToken(token)) {
            throw new BadCredentialsException("Invalid JWT token");
        }

        // 토큰에서 사용자 정보 추출

        long userId = jwtTokenProvider.getUserId(token);


        // 인증 성공 시 Authentication 반환
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        return new JWTAuthenticationToken(userId, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {

        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }


}