package com.bugimarket.security.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal; // 다양한 타입의 사용자 정보

    public JWTAuthenticationToken(Object principal) {
        super(null); // 권한 정보 없이 생성
        this.principal = principal;
        setAuthenticated(false); // 인증 상태 초기화
    }

    public JWTAuthenticationToken(Object principal, List<GrantedAuthority> authorities) {
        super(authorities); // 권한 정보를 전달
        this.principal = principal;
        setAuthenticated(true); // 인증
    }




    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal; // 사용자 정보 반환
    }
}