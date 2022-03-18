package com.movieserver.controller;

import java.util.List;

import com.movieserver.model.Actor;
import com.movieserver.service.ActorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/actor-management"
)
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping(
        path = "/actors",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll() {
     
        List<Actor> actors;

        try {
            actors = actorService.findAll();

            return new ResponseEntity<>(actors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}
