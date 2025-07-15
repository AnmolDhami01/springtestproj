package com.newSpring.testApp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtil {
    @Autowired
    private HttpServletRequest request;

    private String SECRET_KEY = "your-secret-key-must-be-at-least-256-bits-long-for-hs256";
    public final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (SignatureException e) {
            request.setAttribute("statusCode", "15");
            request.setAttribute("statusMessage", "Invalid Jwt Signature");
        } catch (MalformedJwtException e) {
            request.setAttribute("statusCode", "15");
            request.setAttribute("statusMessage", "Jwt Malformed");
        } catch (ExpiredJwtException e) {
            request.setAttribute("statusCode", "15");
            request.setAttribute("statusMessage", "Jwt Token Expired");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("statusCode", "15");
            request.setAttribute("statusMessage", "Unsupported Jwt");
        } catch (IllegalArgumentException e) {
            request.setAttribute("statusCode", "15");
            request.setAttribute("statusMessage", "Illegal Argument");
        }
        return null;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Method to generate token with username (for your AuthServiceImpl)
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Method to generate token with UserDetails (for Spring Security)
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    public Boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}