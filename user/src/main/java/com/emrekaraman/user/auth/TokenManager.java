package com.emrekaraman.user.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
public class TokenManager {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email){
        String jws = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
        return jws;
    }

    public boolean tokenValidate(String token) {
        if (getEmailByToken(token) != null){
            return true;
        }
        return false;
    }

    public String getEmailByToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
