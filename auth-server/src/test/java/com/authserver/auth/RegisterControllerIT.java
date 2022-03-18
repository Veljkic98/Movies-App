package com.authserver.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.authserver.constants.AuthConstants;
import com.authserver.domain.dto.request.CreateUserRequest;

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
public class RegisterControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void registerUser_credentialsOk_ok() {
        CreateUserRequest req = new CreateUserRequest(
            AuthConstants.NEW_USER_FIRSTNAME, 
            AuthConstants.NEW_USER_LASTNAME, 
            AuthConstants.USER_EMAIL_NOT_EXISTS_IN_DB, 
            AuthConstants.NEW_USER_PASSWORD, 
            AuthConstants.NEW_USER_PASSWORD
        );

        ResponseEntity<?> entity = restTemplate.postForEntity("/auth/register", req, CreateUserRequest.class);

        assertEquals(entity.getStatusCode().value(), HttpStatus.CREATED.value());
        assertNotNull(entity.getHeaders().get("Authorization"));
    }

    @Test
    public void registerUser_invalidUsername_conflict() {
        CreateUserRequest req = new CreateUserRequest(
            AuthConstants.NEW_USER_FIRSTNAME, 
            AuthConstants.NEW_USER_LASTNAME, 
            AuthConstants.USER_EMAIL_EXISTS_IN_DB, 
            AuthConstants.NEW_USER_PASSWORD, 
            AuthConstants.NEW_USER_PASSWORD
        );

        ResponseEntity<String> entity = restTemplate.postForEntity("/auth/register", req, String.class);

        assertEquals(entity.getStatusCode().value(), HttpStatus.CONFLICT.value());
        assertNull(entity.getHeaders().get("Authorization"));
    }

    @Test
    public void registerUser_invalidRepassword_conflict() {
        CreateUserRequest req = new CreateUserRequest(
            AuthConstants.NEW_USER_FIRSTNAME, 
            AuthConstants.NEW_USER_LASTNAME, 
            AuthConstants.USER_EMAIL_NOT_EXISTS_IN_DB, 
            AuthConstants.NEW_USER_PASSWORD, 
            AuthConstants.NEW_USER_REPASSWORD_INVALID
        );

        ResponseEntity<String> entity = restTemplate.postForEntity("/auth/register", req, String.class);

        assertEquals(entity.getStatusCode().value(), HttpStatus.CONFLICT.value());
        assertNull(entity.getHeaders().get("Authorization"));
    }
    
}
