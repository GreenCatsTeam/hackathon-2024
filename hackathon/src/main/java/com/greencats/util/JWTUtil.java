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

    public static final String USER_DETAILS = "User details";

    public static final String EMAIL = "email";

    public static final String TEAM_AND_PROJECT = "com.greencats.hackhathon";

    @Value("${secrets.jwt_secret}")
    private String secret;

    @SuppressWarnings("MagicNumber")
    public String generateToken(Long id, String email, String password) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
            .withSubject(USER_DETAILS)
            .withClaim("id", id)
            .withClaim(EMAIL, email)
            .withClaim("password", password)
            .withIssuedAt(new Date())
            .withIssuer(TEAM_AND_PROJECT)
            .withExpiresAt(expirationDate)
            .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
            .withSubject(USER_DETAILS)
            .withIssuer(TEAM_AND_PROJECT)
            .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(EMAIL).asString(); // ?? more fields?? ??
    }
}
