package com.reportserver.domain.exception.advisor;

import com.reportserver.domain.exception.exceptions.NoContentException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalAdvisor {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> accessDenied(AccessDeniedException e) {
        
        return new ResponseEntity<String>("Authorization error occured.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> noContent(NoContentException e) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
