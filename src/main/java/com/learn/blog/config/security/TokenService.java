package com.learn.blog.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String SECRET;
    @Value("${api.security.issuer}")
    private String ISSUER;

    public String getToken(String username){
        return JWT
                .create()
                .withIssuer(this.ISSUER)
                .withExpiresAt(generateExpirationDate())
                .withSubject(username)
                .sign(Algorithm.HMAC256(this.SECRET));
    }

    public String verify(String token){
        try{
        return JWT
                .require(Algorithm.HMAC256(this.SECRET))
                .withIssuer(this.ISSUER)
                .build()
                .verify(token)
                .getSubject();
        }catch (JWTDecodeException e){
            return null;
        }
    }
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
