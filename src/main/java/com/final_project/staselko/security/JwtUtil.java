package com.final_project.staselko.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger LOGGER = LogManager.getLogger(JwtUtil.class);
    private final SecretKey jwtAccessSecret;

    public JwtUtil(@Value("${jwt.secret.access}") String jwtAccessSecret) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
    }

    public String generateToken(UserDetails userDetails) {
        final String role = String.valueOf(userDetails.getAuthorities().stream().findFirst().orElseThrow());
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(3000).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", role)
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            LOGGER.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            LOGGER.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            LOGGER.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            LOGGER.error("Invalid signature", sEx);
        } catch (Exception e) {
            LOGGER.error("invalid token", e);
        }
        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }


    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean checkToken(String token, UserDetails userDetails) {
        final Claims claims = getAccessClaims(token);
        String username = getUsernameFromToken(token);
        boolean isExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isExpired);
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = getAccessClaims(token);
        return claims.getSubject();
    }

    public String getRoleByUserFromToken(String token){
        final Claims claims = getAccessClaims(token);
        return (String) claims.get("role");
    }
}
