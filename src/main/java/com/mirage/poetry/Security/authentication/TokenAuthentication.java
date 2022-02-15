package com.mirage.poetry.Security.authentication;

import com.mirage.poetry.Domains.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class TokenAuthentication implements Authentication {

    private User principal;
    private boolean isAuthenticated;
    private final String token;


    public TokenAuthentication(String token  , User principal) {
        this.token = token;
        this.principal = principal;
        this.isAuthenticated = true;
    }

    public TokenAuthentication(String token){
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return principal.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return principal.getUsername();
    }
}
