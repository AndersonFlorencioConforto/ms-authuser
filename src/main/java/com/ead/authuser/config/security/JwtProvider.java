package com.ead.authuser.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JwtProvider {

    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;
    @Value("${ead.auth.jwtExpirationMs}")
    private int jwtExpirationMs;


    public String generatJwt(Authentication authentication){
        UserDetails userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
}
