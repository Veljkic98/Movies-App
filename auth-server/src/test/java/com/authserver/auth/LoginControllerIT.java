package com.authserver.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.authserver.constants.AuthConstants;
import com.authserver.domain.dto.request.UserLoginRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoginControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void loginUser_credentialsOk_ok() {
        
        UserLoginRequest req = new UserLoginRequest(
            AuthConstants.USER_EMAIL_EXISTS_IN_DB, 
            AuthConstants.USER_PASSWORD_LOGIN_VALID
        );

        ResponseEntity<?> entity = restTemplate.postForEntity("/auth/login", req, UserLoginRequest.class);

        assertEquals(entity.getStatusCode().value(), HttpStatus.OK.value());
        assertNotNull(entity.getHeaders().get("Authorization"));
    }

    @Test
    public void loginUser_wrongUsername_unauthorized() {
        
        UserLoginRequest req = new UserLoginRequest(
            AuthConstants.USER_EMAIL_NOT_EXISTS_IN_DB, 
            AuthConstants.USER_PASSWORD_LOGIN_VALID
        );

        ResponseEntity<String> entity = restTemplate.postForEntity("/auth/login", req, String.class);

        assertEquals(entity.getStatusCode().value(), HttpStatus.UNAUTHORIZED.value());
        assertNull(entity.getHeaders().get("Authorization"));
    }

    @Test
    public void loginUser_wrongPassword_unauthorized() {
        
        UserLoginRequest req = new UserLoginRequest(
            AuthConstants.USER_EMAIL_EXISTS_IN_DB, 
            AuthConstants.USER_PASSWORD_LOGIN_INVALID
        );

        ResponseEntity<String> entity = restTemplate.postForEntity("/auth/login", req, String.class);

        assertEquals(entity.getStatusCode().value(), HttpStatus.UNAUTHORIZED.value());
        assertNull(entity.getHeaders().get("Authorization"));
    }

}
