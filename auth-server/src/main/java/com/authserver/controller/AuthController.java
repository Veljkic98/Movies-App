package com.authserver.controller;

import java.net.URI;
import java.util.Date;

import com.authserver.domain.dto.request.CreateUserRequest;
import com.authserver.domain.dto.request.UserLoginRequest;
import com.authserver.domain.mapper.CreateUserMapper;
import com.authserver.model.Event;
import com.authserver.model.User;
import com.authserver.security.JwtTokenUtil;
import com.authserver.service.ProducerService;
import com.authserver.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping(
    value = "/auth"
)
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private CreateUserMapper createUserMapper;

    @Autowired
	private ProducerService producerService;

    @PostMapping(
        path = "/login",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) throws Exception {

        User user = userService.login(request);

        Event event = new Event(
            user.getEmail(), 
            "User login.", 
            new Date()
        );

        producerService.send(event);

        String encryptedJwt = jwtTokenUtil.generateAccessToken(user);

        return ResponseEntity.ok()
            .header(
                HttpHeaders.AUTHORIZATION,
                encryptedJwt
            )
            .build();
    }

    @PostMapping(
        path = "/register", 
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> register(@RequestBody CreateUserRequest request) throws Exception {

        User user;

        user = userService.create(request);

        Event event = new Event(
            user.getEmail(), 
            "User registration.", 
            new Date()
        );

        producerService.send(event);

        URI location = ServletUriComponentsBuilder
            .fromHttpUrl("http://localhost:8081/user")
            .path("/{id}")
            .buildAndExpand(user.getId())
            .toUri();

        return ResponseEntity.created(location)
            .header(
                HttpHeaders.AUTHORIZATION,
                jwtTokenUtil.generateAccessToken(user)
            )
            .body(createUserMapper.toDto(user));
    }

}
