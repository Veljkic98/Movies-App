package com.movieserver.security;

import java.util.List;

import com.movieserver.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenUtil {

    @Value("${com.levi9.movie-app.auth-server.jwt.secret}")
    private String jwtSecret;

    public User parseToken(String token) {

        Claims body = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        User user = new User();
        user.setEmail((String) body.get("sub"));

        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList((String) body.get("role"));

        user.setAuthorities(authorityList);

        return user;
    }

    public boolean validate(String token) {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
