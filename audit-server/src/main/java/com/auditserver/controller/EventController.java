package com.auditserver.controller;

import java.util.Date;
import java.util.List;

import com.auditserver.model.Event;
import com.auditserver.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/event-management"
)
public class EventController {
    
    @Autowired
    private EventService eventService;

    @GetMapping(
        path = "/events",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAll() {

        List<Event> events = eventService.findAll();

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(
        path = "/events/{userEmail}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllEventsByUserEmail(@PathVariable String userEmail) {

        List<Event> events = eventService.getEventsByUserEmail(userEmail);

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(
        path = "/events/date/{startDate}/{endDate}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllEventsBetweenDates(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startDate, 
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endDate
    ) {

        List<Event> events = eventService.findAllByStartDateLessThanTimeAndEndDateGreaterThanTime(endDate, startDate);

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

}
