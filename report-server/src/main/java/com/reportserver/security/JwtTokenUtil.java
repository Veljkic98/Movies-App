package com.reportserver.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.reportserver.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenUtil {

    @Autowired
    private HttpServletRequest context;

    @Value("${com.levi9.movie-app.jwt.secret}")
    private String jwtSecret;

    public String getToken() {

        final String header = context.getHeader(HttpHeaders.AUTHORIZATION);
        
        final String token = header.split(" ")[1].trim();

        return token;
    }

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

    public HttpHeaders getAuthorizationHeader() {

        HttpHeaders headers = new HttpHeaders();

        String token = getToken();

        headers.set("Authorization", "Bearer " + token);

        return headers;
    }

}
