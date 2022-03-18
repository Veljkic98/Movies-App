package com.reportserver.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;
import com.reportserver.domain.dto.MovieReport;
import com.reportserver.model.Actor;
import com.reportserver.model.Event;
import com.reportserver.model.User;
import com.reportserver.service.actor.ActorService;
import com.reportserver.service.kafka.ProducerService;
import com.reportserver.service.movie.MovieService;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/movie-reporting"
)
public class MovieReportController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorService actorService;

    @Autowired
	private ProducerService producerService;

    @GetMapping(
        path = "/download/movies/excel",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllMoviesExcel(@AuthenticationPrincipal User user) throws IOException {

        List<MovieReport> movies = movieService.getAll();

        movieService.createExcel(movies, "movies");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all movies in Excel format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all movies in Excel format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/movies/csv",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllMoviesCsv(@AuthenticationPrincipal User user) throws IOException {

        List<MovieReport> movies = movieService.getAll();

        movieService.createCSV(movies, "movies");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all movies in CSV format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all movies in CSV format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/movies/pdf",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> downloadAllMoviesPdf(@AuthenticationPrincipal User user) throws IOException, DocumentException {

        List<MovieReport> movies = movieService.getAll();

        movieService.createPdf(movies, "movies");

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all movies in PDF format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all movies in PDF format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/movies/{movieId}/actors/csv",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> allActorsByMovieIdCsv(
        @PathVariable Long movieId, 
        @AuthenticationPrincipal User user
    ) throws Exception {

        List<Actor> actors = movieService.getAllActorsByMovieId(movieId);

        actorService.createCSV(actors, "actors by movie with ID " + movieId);

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all actors of movie with id '" + movieId + "' in CSV format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all actors of movie with id '" + movieId + "' in CSV format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/movies/{movieId}/actors/excel",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> allActorsByMovieIdExcel(
        @PathVariable Long movieId, 
        @AuthenticationPrincipal User user
    ) throws Exception {

        List<Actor> actors = movieService.getAllActorsByMovieId(movieId);

        actorService.createExcel(actors, "actors by movie with ID " + movieId);

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all actors of movie with id '" + movieId + "' in Excel format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all actors of movie with id '" + movieId + "' in Excel format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(
        path = "/download/movies/{movieId}/actors/pdf",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> allActorsByMovieIdPdf(
        @PathVariable Long movieId, 
        @AuthenticationPrincipal User user
    ) throws Exception {

        List<Actor> actors = movieService.getAllActorsByMovieId(movieId);

        actorService.createPdf(actors, "actors by movie with ID " + movieId);

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully download report of all actors of movie with id '" + movieId + "' in PDF format."
        );

        Event event = new Event(
            user.getEmail(), 
            "User try to download report of all actors of movie with id '" + movieId + "' in PDF format.", 
            new Date()
        );

        producerService.send(event);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
