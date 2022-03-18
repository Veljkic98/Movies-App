package com.reportserver.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.reportserver.model.Actor;
import com.reportserver.model.Event;
import com.reportserver.model.User;
import com.reportserver.service.actor.ActorService;
import com.reportserver.service.kafka.ProducerService;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/actor-reporting"
)
public class ActorReportController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private ActorService actorService;

    @Autowired
	private ProducerService producerService;

    @GetMapping(
        path = "/download/actors/csv",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllActorsCsv(@AuthenticationPrincipal User user) throws IOException {

        List<Actor> actors = actorService.getAll();

        actorService.createCSV(actors, "actors");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all actors in CSV format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all actors in CSV format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/actors/excel",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllActorsExcel(@AuthenticationPrincipal User user) throws IOException {

        List<Actor> actors = actorService.getAll();

        actorService.createExcel(actors, "actors");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all actors in Excel format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all actors in Excel format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/actors/pdf",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllActorsPdf(@AuthenticationPrincipal User user) throws DocumentException, IOException {

        List<Actor> actors = actorService.getAll();

        actorService.createPdf(actors, "actors");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all actors in PDF format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all actors in PDF format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
