package com.binnacle.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private static String secret_key = "sL09Ldun";
    private static Algorithm algorithm = Algorithm.HMAC256(secret_key);
    public String create (String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("binnacle")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(algorithm);
    }
    public boolean isValid (String jwt) {
        try{
            JWT.require(algorithm)
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsernameByToken (String jwt) {
        return JWT.require(algorithm)
                .build()
                .verify(jwt)
                .getSubject();
    }

}
