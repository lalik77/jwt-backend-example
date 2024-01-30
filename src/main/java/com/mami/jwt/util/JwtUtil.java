package com.mami.jwt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mami.jwt.controller.JwtController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtController.class);

    private static final String SECRET_KEY = "mamialexandre.fr";

    private static final int TOKEN_VALIDITY = 3600 * 5 * 1000;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String generateToken(final UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Date getExpirationDateFromToken(final String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean isValidToken(final String token, UserDetails userDetails) {
        final String userName = getUsernameFromToken(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(final String token) {

        String payload = getClaimsFromToken(token, Claims::getSubject);

        logger.info("JwtUtil#getUsernameFromToken(): {}", payload);
        return payload;
       /* JsonNode payloadJson = null;
        try {
            payloadJson = objectMapper.readTree(payload);
        } catch (JsonProcessingException e) {
            logger.error("JwtUtil#getUsernameFromToken(): {}",e);;
           // e.printStackTrace();
        }

        if (payloadJson != null) {
            return payloadJson.get("userName").asText();
        } else {
            return payload;
        }*/
    }

    public <T> T getClaimsFromToken(final String token, Function<Claims, T> claimResolve) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolve.apply(claims);
    }


    private Claims getAllClaimsFromToken(final String token) {

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
