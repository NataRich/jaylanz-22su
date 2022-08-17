package com.jaylanz.common.util;

import com.jaylanz.common.exception.InvalidTokenException;
import com.jaylanz.common.property.AppJwtProperty;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final AppJwtProperty prop;

    @Autowired
    public JwtUtil(AppJwtProperty prop) {
        this.prop = prop;
    }

    public String createToken(long userId, String username, String ipAddress) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + prop.getExpiration()))
                .claim("id", userId)
                .claim("username", username)
                .claim("ip", ipAddress)
                .signWith(SignatureAlgorithm.HS512, prop.getSecret())
                .compact();
    }

    public boolean isValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    public Long getUserId(String token) {
        return Long.valueOf(String.valueOf(parseClaims(token).get("id")));
    }

    public String getUsername(String token) {
        return (String) parseClaims(token).get("username");
    }

    public String getIpAddress(String token) {
        return (String) parseClaims(token).get("ip");
    }

    public Date getIssueAt(String token) {
        return parseClaims(token).getIssuedAt();
    }

    public Date getExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    private Claims parseClaims(String token) throws InvalidTokenException {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(prop.getSecret()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new InvalidTokenException();
        }
        return claims;
    }
}
