package com.reportserver.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.reportserver.model.Director;
import com.reportserver.model.Event;
import com.reportserver.model.User;
import com.reportserver.service.director.DirectorService;
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
    path = "/director-reporting"
)
public class DirectorReportController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private DirectorService directorService;

    @Autowired
	private ProducerService producerService;

    @GetMapping(
        path = "/download/directors/csv",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllDirectorsCsv(@AuthenticationPrincipal User user) throws Exception {

        List<Director> directors = directorService.getAll();

        directorService.createCSV(directors, "directors");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully look up for report of all directors in CSV format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all directors in CSV format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/directors/excel",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllDirectorsExcel(@AuthenticationPrincipal User user) throws IOException {

        List<Director> directors = directorService.getAll();

        directorService.createExcel(directors, "directors");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully look up for report of all directors in Excel format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all directors in Excel format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/directors/pdf",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllDirectorsPdf(@AuthenticationPrincipal User user) throws IOException, DocumentException {

        List<Director> directors = directorService.getAll();

        directorService.createPdf(directors, "directors");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully look up for report of all directors in PDF format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all directors in PDF format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
