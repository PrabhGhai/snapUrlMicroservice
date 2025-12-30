package com.snapUrl.User.Service.utils;

import com.snapUrl.User.Service.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    private SecretKey getKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(UserEntity user) {
        return generateToken(
                user.getUsername(),
                accessExpiration,
                accessSecret,
                Map.of("type", "ACCESS", "uuid", user.getId(), "role", user.getRole())
        );
    }

    public String generateRefreshToken(UserEntity user) {
        return generateToken(
                user.getUsername(),
                refreshExpiration,
                refreshSecret,
                Map.of("type", "REFRESH")
        );
    }

    private String generateToken(
            String subject,
            long expiration,
            String secret,
            Map<String, Object> claims
    ) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, getKey(secret))
                .compact();
    }

    //Token Validation

    public boolean isTokenValid(String token, UserDetails userDetails, boolean isRefreshToken) {
        final String username = extractUsername(token, isRefreshToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token, isRefreshToken);
    }

    public boolean isTokenExpired(String token, boolean isRefreshToken) {
        return extractExpiration(token, isRefreshToken).before(new Date());
    }

    //Extraction

    public String extractUsername(String token, boolean isRefreshToken) {
        return extractAllClaims(token, isRefreshToken).getSubject();
    }

    public Date extractExpiration(String token, boolean isRefreshToken) {
        return extractAllClaims(token, isRefreshToken).getExpiration();
    }

    private Claims extractAllClaims(String token, boolean isRefreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(isRefreshToken ? refreshSecret : accessSecret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //checking claims
    public boolean isRefreshToken(String token) {
        Claims claims = extractAllClaims(token, true);
        return "REFRESH".equals(claims.get("type"));
    }

}
