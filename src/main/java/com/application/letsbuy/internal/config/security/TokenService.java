package com.application.letsbuy.internal.config.security;

import com.application.letsbuy.internal.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${LetsBuy.jwt.expiration}")
    private String expiration;

    @Value("${LetsBuy.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        User logged = (User) authentication.getPrincipal();
        Date today = new Date();
        Date dateExpiration = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API da LetsBuy")
                .setSubject(logged.getId().toString())
                .setIssuedAt(today)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public Long getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
