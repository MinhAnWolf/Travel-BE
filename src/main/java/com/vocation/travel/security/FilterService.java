package com.vocation.travel.security;

import com.vocation.travel.common.enumerator.CommonEnum;
import com.vocation.travel.entity.Token;
import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.service.serviceImpl.internal.StorageTokenServiceImpl;
import com.vocation.travel.util.Utils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class FilterService extends OncePerRequestFilter {
    private final String REFRESH = "rf";
    private final String AUTHORIZATION = "Authorization";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private StorageTokenServiceImpl storageToken;

    /**
     * Do filter internal.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filterChain FilterChain
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String UID = "c_id";
        String tokenAuthorization = request.getHeader(AUTHORIZATION);
        String tokenRf = request.getHeader(REFRESH);
        String uid = request.getHeader(UID);
        final String uriLogin = "/api/v1/travel/auth/login";
        final String uriRegister = "/api/v1/travel/auth/register";
        boolean checkLoginUrl = request.getRequestURI().equals(uriLogin);
        boolean checkRegisterUrl = request.getRequestURI().equals(uriRegister);
        boolean urlPassFilter = urlPassFilter(request.getRequestURI());

        // Check diff url register and login require jwt
        if (!urlPassFilter) {
            if (checkJwt(tokenAuthorization, tokenRf)  && !checkLoginUrl && !checkRegisterUrl) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // Pass url
        if (checkLoginUrl || checkRegisterUrl || urlPassFilter) {
            filterChain.doFilter(request, response);
            return;
        }

        // Check token
        if(!Utils.isEmpty(UID) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = getUserDetailsByUid(uid);
            List<Token> listToken = new ArrayList<>();
            Token token = new Token();
            token.setAccess(tokenAuthorization);
            token.setRefresh(tokenRf);
            listToken.add(token);
            Map<String, String> reNewToken = new HashMap<>();
            // Check token, if token expired then re-generate token and add header response
            for (Token itemToken: listToken) {
                try {
                    // Check access token occur then check refresh token
                   if (tokenService.validateToken(itemToken.getAccess(), userDetails)) {
                       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                       return;
                   }
                // catch for access expired
                } catch (ExpiredJwtException e) {
                    // Get token from database
                    Token tokenGetByAccess = storageToken.read(itemToken);
                    try {
                        // Check refresh token
                        if(tokenService.validateToken(itemToken.getRefresh(), userDetails)) {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        } else {
                            // when refresh token not refresh expired
                            if (Objects.equals(tokenGetByAccess.getRefresh(), tokenRf)) {
                                reNewToken.put(AUTHORIZATION, tokenService.generateToken(userDetails.getUsername(),
                                    CommonEnum.typeToken.ACCESS.name()));
                            } else {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                return;
                            }
                        }
                    // catch for refresh expired
                    } catch (ExpiredJwtException ex) {
                        // generate refresh token and access token when refresh expired
                        if (!Utils.objNull(tokenGetByAccess)
                            && !Utils.isEmpty(tokenGetByAccess.getRefresh())
                            && Objects.equals(tokenGetByAccess.getRefresh(), tokenRf)) {
                            reNewToken.put(REFRESH, tokenService.generateToken(userDetails.getUsername(),
                                CommonEnum.typeToken.RF.name()));
                            reNewToken.put(AUTHORIZATION, tokenService.generateToken(userDetails.getUsername(),
                                CommonEnum.typeToken.ACCESS.name()));
                        } else {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            return;
                        }
                    }
                }
            }
            if (!reNewToken.isEmpty()) {
                addHeader(reNewToken, uid, response);
            }
            setSecurityContextHolder(userDetails, request);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Add header response.
     *
     * @param listToken Map<String, String>
     * @param uid String
     * @param response HttpServletResponse
     * */
    private void addHeader(Map<String, String> listToken, String uid, HttpServletResponse response) {
        if (Utils.isEmpty(listToken.get(REFRESH))) {
            response.setHeader(REFRESH, listToken.get(REFRESH));
        } else if(Utils.isEmpty(listToken.get(AUTHORIZATION))) {
            response.setHeader(AUTHORIZATION, listToken.get(AUTHORIZATION));
        }
        response.setHeader("c_id", uid);
        response.setContentType("application/json");
    }

    /**
     * Url pass filter.
     *
     * @param requestUrl String
     * @return boolean
     * */
    private boolean urlPassFilter(String requestUrl) {
        return requestUrl.contains("swagger") || requestUrl.contains("api-docs")
                || requestUrl.contains("address");
    }

    /**
     * Check jwt.
     *
     * @param auth String
     * @param rf   String
     * @return boolean
     * */
    private boolean checkJwt(String auth, String rf) {
        return Utils.isEmpty(auth) || Utils.isEmpty(rf) || !auth.startsWith("Bearer ") || !rf.startsWith("Bearer ");
    }

    /**
     *
     * */
    private UserDetails getUserDetailsByUid(String UID) {
        String username = userRepository.getUsernameById(UID);
        return userDetailsService.loadUserByUsername(username);
    }

    private void setSecurityContextHolder(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}