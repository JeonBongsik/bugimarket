package com.bugimarket.security.provider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private SecretKey secretKey;

    private Long expiration_time;

    @Autowired
    public JwtTokenProvider(@Value("${token.secret}") String secret, @Value("${token.expiration_time}") long expiration_time) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expiration_time = expiration_time;
    }

    public Long getUserId(String token) {

        String subject = Jwts.parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload()
                .getSubject(); // subject에 저장된 userId를 반환

        return Long.valueOf(subject);
    }

    // 추후 활용
//    public String getRole(String token) {
//
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
//    }



    public String createJwt(String userId) {

        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration_time))
                .signWith(secretKey)
                .compact();

    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token); // 서명 및 구조 검증
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token is expired: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }


}