package com.vocation.travel.security;

import com.vocation.travel.common.enumerator.CommonEnum;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.security.config.RsaKeyConfigProperties;
import com.vocation.travel.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Logic authentication and authorization.
 *
 * @author  Minh An
 * */
@SuppressWarnings("ALL")
@Log4j2
@Service
public class TokenService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RsaKeyConfigProperties rsaKeyConfigProperties;

    @Autowired
    private UserService userService;

    private final String BEARER = "Bearer";
    private final String BEARER_SPACE = "Bearer ";
    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    /**
     * Generate token.
     *
     * @param username String
     * @return String
     * */
    public String generateToken(String username, String typeToken){
        Map<String, Object> claims = new HashMap<>();
        if (Objects.equals(typeToken, CommonEnum.typeToken.ACCESS)) {
            return createToken(claims, username, +1000*60*1);
        }
        return createToken(claims, username, +10000*600*2);
    }

    private String createToken(Map<String, Object> claims, String username, int timeExpiration) {
        return BEARER_SPACE + Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*1))
            .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parser()
            .setSigningKey(getSignKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Get username from jwt
     *
     * @return String
     * */
    private String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validate time token.
     *
     * @param token String
     * @return boolean
     * */
    public boolean validateToken(String token, UserDetails userDetails) {
        return isTokenExpired(token) && !userDetails.getUsername().equals(extractUsername(token));
    }
}
