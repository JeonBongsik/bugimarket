package com.bugimarket.security.filter;

import com.bugimarket.security.dto.JWTAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import java.io.IOException;

public class JWTAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super("/**");
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String path = request.getRequestURI();
        String method = request.getMethod();

        if (request.getAttribute("FILTER_PROCESSED") != null) {
            return false; // 이미 처리된 요청은 필터 실행 안 함
        }

        // 특정 경로 제외
        if ("/login".equals(path) || ("/users".equals(path) && "POST".equalsIgnoreCase(method))) {
            return false; // 필터 실행하지 않음
        }

        return super.requiresAuthentication(request, response); // 나머지 경로에 대해 필터 실행

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {


        // request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");


        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new AuthenticationServiceException("Authorization header not found or invalid");
        }

        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // JWT 토큰을 JWTAuthenticationToken으로 변환
        JWTAuthenticationToken authToken = new JWTAuthenticationToken(token);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 세션에 사용자 등록

        SecurityContextHolder.getContext().setAuthentication(authResult);
        request.setAttribute("FILTER_PROCESSED", true);
        chain.doFilter(request, response);
        // 검증 성공
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException ,ServletException{

        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("{\"message\": \" 토큰 검증 실패. 잘못된 토큰입니다. \"}");
        response.getWriter().flush();

    }
}

