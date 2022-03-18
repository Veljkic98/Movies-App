package com.authserver.domain.exception.advisor;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAdvisor {

    /**
     * Exception occurs if password is wrong.
     * 
     * @return Unauthorized response (401)
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> wrondPassword(BadCredentialsException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization error occured.");
    }

    /**
     * Exception occurs if username is wrong.
     * 
     * @return Unauthorized response (401)
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<String> wrongUsername(InternalAuthenticationServiceException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization error occured.");
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> badRegistrationCredentials(ValidationException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    
}
