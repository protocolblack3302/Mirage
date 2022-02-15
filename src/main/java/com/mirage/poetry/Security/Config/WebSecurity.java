package com.mirage.poetry.Security.Config;

import com.mirage.poetry.Security.filters.CustomBasicAuthFilter;
import com.mirage.poetry.Security.filters.JwtAuthenticationFilter;
import com.mirage.poetry.Security.providers.TokenAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userdetailsService;
    private final TokenAuthProvider tokenAuthProvider;

    @Value("${secretKey}")
    private String secret;
    @Value("${expiryTime}")
    private String expiryTime;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userdetailsService).passwordEncoder(getPasswordEncoder());
        auth.authenticationProvider(tokenAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/register", "/login","/connectUrl").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomBasicAuthFilter(getManager(), secret, Long.parseLong(expiryTime)));
        http.addFilterAfter(new JwtAuthenticationFilter(getManager()), CustomBasicAuthFilter.class);

    }

    @Bean
    public AuthenticationManager getManager() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
