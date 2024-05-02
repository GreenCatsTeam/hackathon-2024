package com.greencats.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {

    @Value("${secrets.jwt_secret}")
    private String secret;

    public String generateToken(Long id, String email, String password) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
            .withSubject("User details")
            .withClaim("id", id)
            .withClaim("email", email)
            .withClaim("password", password)
            .withIssuedAt(new Date())
            .withIssuer("com.greencats.hackhathon")
            .withExpiresAt(expirationDate)
            .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
            .withSubject("User details")
            .withIssuer("com.greencats.hackhathon")
            .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString(); // ?? more fields?? ??
    }
}
