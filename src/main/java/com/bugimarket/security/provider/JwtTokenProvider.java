package com.bugimarket.security.provider;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {



    private SecretKey secretKey;

    private Long expiration_time;
    @Autowired
    public JwtTokenProvider(@Value("${token.secret}")String secret,@Value("${token.expiration_time}")long expiration_time) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.expiration_time = expiration_time;
    }

    public Long getUserId (String token) {

        String subject =  Jwts.parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload()
                .getSubject(); // subject에 저장된 userId를 반환

        return Long.valueOf(subject);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String userId) {

        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration_time))
                .signWith(secretKey)
                .compact();

    }


}