package com.railway.auth_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //decodes your hex secret into a cryptographic key. Called internally only
    private Key getSigningKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //builds a signed JWT with email as subject, role as a custom claim, issued time, and expiry.
    // Called after successful login/register
    public String generateToken(String email, String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //reads the subject field from the token payload. Called by the filter on every request
    public String extractEmail(String token){
        return parseClaims(token).getSubject();
    }

    //reads the custom role claim. Used for authorization decisions
    public String extractRole(String token){
        return (String) parseClaims(token).get("role");
    }

    //tries to parse the token. If it's expired, tampered with, or malformed, parseClaims() throws a JwtException and we return false
    public boolean isTokenValid(String token){
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    //the core parser. Verifies the signature using your secret key and returns the payload
    private Claims parseClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
