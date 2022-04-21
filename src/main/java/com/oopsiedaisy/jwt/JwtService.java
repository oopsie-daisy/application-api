package com.oopsiedaisy.jwt;

import com.oopsiedaisy.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static java.lang.System.currentTimeMillis;
import static java.util.UUID.fromString;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String generateToken(UUID userUuid) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userUuid);
    }
    
    public String getUserUuidFromJwt(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UUID userUuid) {
        final String uuidFromJwt = getUserUuidFromJwt(token);
        return (userUuid.equals(fromString(uuidFromJwt)) && !isTokenExpired(token)) && Jwts.parser().isSigned(token);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecret()).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String doGenerateToken(Map<String, Object> claims, UUID userUuid) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userUuid.toString())
                .setIssuedAt(new Date(currentTimeMillis()))
                .setExpiration(new Date(currentTimeMillis() + jwtProperties.getExpiration() * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret()).compact();
    }
}
