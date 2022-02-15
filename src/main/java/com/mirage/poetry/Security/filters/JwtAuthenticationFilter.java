package com.mirage.poetry.Security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirage.poetry.Security.authentication.TokenAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String token = request.getHeader("Authorization");

        if (token != null && !token.isBlank()) {
            String payload = token.substring("Bearer ".length());
            TokenAuthentication authentication = new TokenAuthentication(payload);
            log.info("got Payload {}", payload);
            Authentication filledAuth = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(filledAuth);
            filterChain.doFilter(request, response);

        } else {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), "Authentication failed");

        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login")
                || request.getServletPath().equals("/register")
                || request.getServletPath().equals("/connectUrl");

    }
}
