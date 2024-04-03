package com.vocation.travel.security;

import com.vocation.travel.common.constant.TimeConstant;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.security.config.RsaKeyConfigProperties;
import com.vocation.travel.util.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                .expiresAt(now.plus(TimeExpires, ChronoUnit.MINUTES))
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
    public String refreshTokenExpires(Collection<? extends GrantedAuthority> roles,
                                       String idUser, String username, int TimeExpires) {
        Instant now = Instant.now();

        String scope = roles.stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Bearer")
                .expiresAt(now.plus(TimeExpires, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope", scope)
                .claim("id", idUser)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Decode jwt.
     *
     * @param token String
     * @return Jwt
     * */
    public Jwt deJwt(String token) {
        JwtDecoder decoder = NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
        return decoder.decode(token);
    }

    /**
     * Validate time token.
     *
     * @param token String
     * @return boolean
     * */
    public boolean validateTimeToken(String token) {
        Jwt jwt = deJwt(token);
        Instant expiresAt = jwt.getExpiresAt();
        if (expiresAt == null) {
            return false;
        }
        return expiresAt.isBefore(Instant.now());
    }

    /**
     * refesh token.
     *
     * @param token String
     * @return boolean
     * */
    public Map<String, String> refeshToken(String refeshToken, String atToken) {
        Map<String, String> listToken = new HashMap<>();
        String sub = "sub";
        String id = "id";
        String scope = "scope";
        refeshToken = naturalVersionToken(refeshToken);
        atToken = naturalVersionToken(atToken);
        // check refesh token
        if (validateTimeToken(refeshToken)) {
            Jwt jwt = deJwt(refeshToken);
            List<String> scopes = (List<String>) jwt.getClaims().get(scope);
            Collection<GrantedAuthority> roles = convertScope(scopes);
            listToken.put("rf", refreshTokenExpires(roles, String.valueOf(jwt.getClaims().get(id)),
                String.valueOf(jwt.getClaims().get(sub)), TimeConstant.minuteRf));
        }

        // check access token
        if (validateTimeToken(atToken)) {
            Jwt jwt = deJwt(atToken);
            List<String> scopes = (List<String>) jwt.getClaims().get(scope);
            Collection<GrantedAuthority> roles = convertScope(scopes);
            listToken.put("Authorization", refreshTokenExpires(roles, String.valueOf(jwt.getClaims().get(id)),
                    String.valueOf(jwt.getClaims().get(sub)), TimeConstant.minuteAt));
        }
        return listToken;
    }

    /**
     * Remove Bearer.
     *
     * @param token String
     * @return String
     * */
    public String naturalVersionToken(String token) {
        if (Utils.isEmpty(token)) {
            return null;
        }
        return token.replace("Bearer ", "");
    }

    private Collection<GrantedAuthority> convertScope(List<String> scopes) {
        Collection<GrantedAuthority> roles = new ArrayList<>();
        for (String roleItem : scopes) {
            GrantedAuthority grantedAuthority = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return roleItem;
                }
            };
            roles.add(grantedAuthority);
        }
        return roles;
    }
}
