package com.vocation.travel.security;


import com.vocation.travel.util.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class FilterService extends OncePerRequestFilter {
    private final String REFRESH = "rf";
    private final String AUTHORIZATION = "Authorization";

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenAuthorization = request.getHeader(AUTHORIZATION);
        String tokenRf = request.getHeader(REFRESH);
        String uriLogin = "/api/v1/travel/auth/login";
        boolean checkLoginUrl = request.getRequestURI().equals(uriLogin);

        if (Utils.isEmpty(tokenAuthorization) && Utils.isEmpty(tokenRf) && !checkLoginUrl) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (checkLoginUrl) {
            filterChain.doFilter(request, response);
            return;
        }

        Map<String, String> listToken =  tokenService.refeshToken(tokenRf, tokenAuthorization);
        if (!Utils.objNull(listToken)) {
            addHeader(listToken, response);
        }

        filterChain.doFilter(request, response);
    }

    private void addHeader(Map<String, String> listToken, HttpServletResponse response) {
        if (Utils.isEmpty(listToken.get(REFRESH))) {
            response.setHeader(REFRESH, listToken.get(REFRESH));
        } else if(Utils.isEmpty(listToken.get(AUTHORIZATION))) {
            response.setHeader(AUTHORIZATION, listToken.get(AUTHORIZATION));
        }
        response.setContentType("application/json");
    }


}
