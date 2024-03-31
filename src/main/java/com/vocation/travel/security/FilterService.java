package com.vocation.travel.security;


import com.vocation.travel.util.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class FilterService extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (!Utils.isEmpty(authorization)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }


}
