package it.epicode.capstoneProject.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.capstoneProject.model.entity.Utente;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("application.properties")
public class JwtTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    @Value("${spring.jwt.expirationMs}")
    private String expirationMs;

    public String createToken(Utente utente){
        return Jwts.builder().subject(utente.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expirationMs)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();
    }


}
