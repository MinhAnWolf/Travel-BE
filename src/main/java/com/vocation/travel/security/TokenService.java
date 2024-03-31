package com.vocation.travel.security;

import com.vocation.travel.common.constant.TimeConstant;
import com.vocation.travel.model.AuthUser;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.security.config.RsaKeyConfigProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

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
    private JwtEncoder jwtEncoder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RsaKeyConfigProperties rsaKeyConfigProperties;

    /**
     * Generate token.
     *
     * @param authentication Authentication
     * @return String
     * */
    public String generateToken(Authentication authentication, String idUser, int TimeExpires) {
        Instant now = Instant.now();

        String scope = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Bearer")
                .expiresAt(now.plus(TimeExpires, ChronoUnit.SECONDS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("id", idUser)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Generate token expires.
     *
     * @param authentication Authentication
     * @return String
     * */
    public String generateTokenExpires(Collection<? extends GrantedAuthority> roles,
                                       String idUser, String username, int TimeExpires) {
        Instant now = Instant.now();

        String scope = roles.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Bearer")
                .expiresAt(now.plus(TimeExpires, ChronoUnit.SECONDS))
                .subject(username)
                .claim("scope", scope)
                .claim("id", idUser)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }



    public Jwt deJwt(String token) {
        JwtDecoder decoder = NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
        return decoder.decode(token);
    }

    /**
     * Validate token.
     *
     * @param token String
     * @return boolean
     * */
    public boolean validateToken(String token) {
        Jwt jwt = deJwt(token);

        //check expires
        Instant expiresAt = jwt.getExpiresAt();
        if (expiresAt == null) {
            return false; // If expiration time is not provided, JWT is considered as not expired
        }
        return expiresAt.isBefore(Instant.now());
    }

    /**
     * refesh token.
     *
     * @param token String
     * @return boolean
     * */
    public String[] refeshToken(String refeshToken, String atToken) {
        String[] result = new String[2];
        String sub = "sub";
        String id = "id";
        String scope = "scope";
        // check refesh token -> result[0]
        if (validateToken(refeshToken)) {
            Jwt jwt = deJwt(refeshToken);
            Collection<? extends GrantedAuthority> roles =
                    (Collection<? extends GrantedAuthority>) jwt.getClaims().get(scope);
            result[0] = generateTokenExpires(roles, String.valueOf(jwt.getClaims().get(id)),
                    String.valueOf(jwt.getClaims().get(sub)), TimeConstant.minuteRf);
        }

        // check access token -> result[1]
        if (validateToken(atToken)) {
            Jwt jwt = deJwt(atToken);
            Collection<? extends GrantedAuthority> roles =
                    (Collection<? extends GrantedAuthority>) jwt.getClaims().get(scope);
            result[1] = generateTokenExpires(roles, String.valueOf(jwt.getClaims().get(id)),
                    String.valueOf(jwt.getClaims().get(sub)), TimeConstant.minuteAt);
        }
        return result;
    }

    //https://www.baeldung.com/spring-security-oauth2-refresh-token-angular
    //https://www.youtube.com/watch?v=LowJMwa7LCU
}
