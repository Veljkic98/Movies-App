package com.authserver.user;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.authserver.constants.AuthConstants;
import com.authserver.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    public void findUser_usernameExists_notNull() {
        UserDetails userDetails = userService.loadUserByUsername(AuthConstants.USER_EMAIL_EXISTS_IN_DB);

        assertNotNull(userDetails);
    }

    @Test
    public void findUser_usernameNotExists_null() {
        UserDetails userDetails = userService.loadUserByUsername(AuthConstants.USER_EMAIL_NOT_EXISTS_IN_DB);

        assertNull(userDetails);
    }
    
}
