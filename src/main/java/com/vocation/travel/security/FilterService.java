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
        String uriLogin = "/api/v1/travel/auth/login";
        String uriRegister = "/api/v1/travel/auth/register";
        boolean checkLoginUrl = request.getRequestURI().equals(uriLogin);
        boolean checkRegisterUrl = request.getRequestURI().equals(uriRegister);
        boolean urlPassFilter = urlPassFilter(request.getRequestURI());

        if (!urlPassFilter) {
            if (Utils.isEmpty(tokenAuthorization) && Utils.isEmpty(tokenRf) && !checkLoginUrl) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        if (checkLoginUrl || checkRegisterUrl || urlPassFilter) {
            filterChain.doFilter(request, response);
            return;
        }

        Map<String, String> listToken =  tokenService.refeshToken(tokenRf, tokenAuthorization, uid);
        if (!Utils.isNull(listToken) && !listToken.isEmpty()) {
            addHeader(listToken, uid, response);
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
}
