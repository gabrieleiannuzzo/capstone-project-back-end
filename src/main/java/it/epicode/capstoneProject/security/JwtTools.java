package it.epicode.capstoneProject.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.capstoneProject.exception.UnauthorizedException;
import it.epicode.capstoneProject.model.entity.Utente;
import jakarta.servlet.http.HttpServletRequest;
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

    public void validateToken(String token){
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception e){
            throw new UnauthorizedException(e.getMessage());
        }
    }

    public String extractUsernameFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String extractUsernameFromAuthorizationHeader(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) throw new UnauthorizedException("Token non presente");
        String token = authorization.substring(7);
        validateToken(token);
        return extractUsernameFromToken(token);
    }

    public String extractUsernameFromAuthorizationHeader(String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) throw new UnauthorizedException("Token non presente");
        String token = authorization.substring(7);
        validateToken(token);
        return extractUsernameFromToken(token);
    }

    public void validateAuthorization(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) throw new UnauthorizedException("Token non valido");
        validateToken(authorization.substring(7));
    }

    public void validateAuthorization(String authorization){
        if (authorization == null || !authorization.startsWith("Bearer ")) throw new UnauthorizedException("Token non valido");
        validateToken(authorization.substring(7));
    }
}
