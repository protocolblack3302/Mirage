package com.mirage.poetry.Security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Base64Utils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@NoArgsConstructor
@Slf4j
public class CustomBasicAuthFilter extends UsernamePasswordAuthenticationFilter {


    private String secret;
    private Long expiryTime;

    private AuthenticationManager authenticationManager;

    public CustomBasicAuthFilter(AuthenticationManager manager , String secret , Long expiryTime) {
       this.authenticationManager = manager;
       this.secret  = secret;
       this.expiryTime = expiryTime;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String full = request.getHeader("Authorization");
        String cred = full.substring("BASIC ".length());
        log.info("Basic auth attempted with {}" , cred);
        String[] str = new String(Base64Utils.decodeFromString(cred)).split(":");
        String username = str[0];
        String password = str[1];


        log.info("attempted to login with {} , {}", username, password);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("Authentication successful");
        String username = authResult.getName();
        Algorithm algo = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() +  expiryTime* 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", authResult
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algo);

        Map<String, String> map = new HashMap<>();
        map.put("authToken", token);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), map);

    }
}
