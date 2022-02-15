package com.mirage.poetry.Security.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class JwtTokenUtil {

    private DecodedJWT jwt;


    public  void verifyToken(String token , String secret) throws JWTVerificationException{

        Algorithm algo = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algo).build(); //Reusable verifier instance
             jwt = verifier.verify(token);

    }


    public String getUsername(){
        return jwt.getSubject();
    }

    public Collection<String> getRoles(){
        return jwt.getClaim("roles").asList(String.class);
    }


}
