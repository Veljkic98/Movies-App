package com.authserver.security;

import com.authserver.model.Authority;
import com.authserver.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${com.levi9.movie-app.auth-server.jwt.secret}")
    private String jwtSecret;

    @Value("${com.levi9.movie-app.auth-server.jwt.issuer}")
    private String jwtIssuer;

    @Value("${com.levi9.movie-app.auth-server.jwt.expiration}")
    private Integer jwtExpiration;

    @Value("${com.levi9.movie-app.private-key.path}")
    private String privateKeyPath;

    public JwtTokenUtil() {
    }

    public String generateAccessToken(User user) throws Exception {

        Authority auth = (Authority) user.getAuthorities().toArray()[0];
        
        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("role", auth.getAuthority())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        AsymmetricCryptography ac = new AsymmetricCryptography();
        PrivateKey privateKey = ac.getPrivate(privateKeyPath);

        String encryptedJwt = ac.encryptText(jwt, privateKey);

        return encryptedJwt;
    }

}
