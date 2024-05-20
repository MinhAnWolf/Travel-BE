package com.vocation.travel.security;

import com.vocation.travel.repository.UserRepository;
import com.vocation.travel.util.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.IOException;
import java.util.Map;

@Component
public class FilterService extends OncePerRequestFilter {
    private final String REFRESH = "rf";
    private final String AUTHORIZATION = "Authorization";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

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

        // Refresh token
        Map<String, String> listToken =  tokenService.refeshToken(tokenRf, tokenAuthorization, uid);
        if (!Utils.objNull(listToken) && !listToken.isEmpty()) {
            addHeader(listToken, uid, response);
        }

        if(Utils.isEmpty(UID) && SecurityContextHolder.getContext().getAuthentication() == null){
            String username = userRepository.getUsernameById(UID);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

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
}