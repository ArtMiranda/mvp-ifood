package br.dev.arturmiranda.mvpifood.util;

import br.dev.arturmiranda.mvpifood.dto.user.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class JWTUtil {

    private static final String SECRET = "A-VERY-HARD-TO-GUESS-SECRET";

    private static final String ISSUER = "mvp-ifood";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    public String generateToken(UserDTO userDTO) {
        try {
            return JWT.create().withIssuer(ISSUER)
                    .withIssuedAt(creationDate())
                    .withExpiresAt(expireDate())
                    .withSubject(userDTO.getEmail())
                    .withClaim("first_name", userDTO.getFirstName())
                    .withClaim("last_name", userDTO.getLastName())
                    .sign(ALGORITHM);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error creating token", e);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            return JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e){
            throw new JWTVerificationException("Invalid or expired token");
        }
    }

    public Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    public Instant expireDate() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(1).toInstant();
    }


    public Boolean validateToken(String token) {
        try {
            JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
