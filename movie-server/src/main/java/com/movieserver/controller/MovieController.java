package com.movieserver.controller;

import java.util.List;

import javax.validation.Valid;

import com.movieserver.domain.dto.request.CreateMovieRequest;
import com.movieserver.domain.dto.request.UpdateMovieRequest;
import com.movieserver.domain.mapper.MovieMapper;
import com.movieserver.model.Actor;
import com.movieserver.model.Movie;
import com.movieserver.service.MovieService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/movie-management"
)
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieMapper movieMapper;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping(
        path = "/movies",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getAll() {

        System.out.println("sssssssssssssssssssssssssssss");

        List<Movie> movies;

        try {
            movies = movieService.findAll();

            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    // end::get-aggregate-root[]

    @GetMapping(
        path = "/movies/{movieId}/actors",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllActors(@PathVariable Long movieId) {
     
        List<Actor> actors;

        try {
            actors = movieService.findAllActorsByMovieId(movieId);

            return new ResponseEntity<>(actors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(
        path = "/movies/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
     
        Movie movie = movieService.findOne(id);

        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @DeleteMapping(
        path = "/movies/{id}"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
     
        movieService.deleteOne(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(
        path = "/movies",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody @Valid CreateMovieRequest request) {
     
        Movie movie = movieService.create(request);

        return new ResponseEntity<>(movieMapper.toDto(movie), HttpStatus.CREATED);
    }

    @PutMapping(
        path = "/movies",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateMovieRequest request) {
     
        Movie movie = movieService.update(request);

        return new ResponseEntity<>(movieMapper.toDto(movie), HttpStatus.CREATED);
    }

}
