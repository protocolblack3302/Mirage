package com.mirage.poetry.Security.providers;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.mirage.poetry.Domains.User;
import com.mirage.poetry.Security.authentication.TokenAuthentication;
import com.mirage.poetry.Security.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class TokenAuthProvider implements AuthenticationProvider {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;


    @Value("${secretKey}")
    private String secret;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String token = (String) authentication.getCredentials();
        Authentication authentication1;
        try {
            jwtTokenUtil.verifyToken(token, secret);
            String foundUsername = jwtTokenUtil.getUsername();
             User  userFoundInDatabase = (User) userDetailsService.loadUserByUsername(foundUsername);
            authentication1 = new TokenAuthentication(token, userFoundInDatabase);
        } catch (JWTVerificationException exception) {
            throw new BadCredentialsException("Token expired , L0gin Again");
        }

        return authentication1;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
