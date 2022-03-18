package com.movieserver.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.movieserver.domain.dto.request.CreateActorRequest;
import com.movieserver.domain.dto.request.CreateDirectorRequest;
import com.movieserver.domain.dto.request.CreateMovieRequest;
import com.movieserver.domain.dto.request.UpdateMovieRequest;
import com.movieserver.domain.mapper.DirectorMapper;
import com.movieserver.domain.mapper.MovieMapper;
import com.movieserver.model.Actor;
import com.movieserver.model.Director;
import com.movieserver.model.Movie;
import com.movieserver.model.User;
import com.movieserver.repository.ActorRepository;
import com.movieserver.repository.DirectorRepository;
import com.movieserver.repository.MovieRepository;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private DirectorMapper directorMapper;

    /**
     * Service method to find all movies.
     * 
     * @return list of movies
     */
    public List<Movie> findAll() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LOG.log(
            Level.INFO, "User with email: '" + user.getEmail() 
                + "' successfully look up for all movies."
        );
        
        return movieRepository.findAll();
    }

    /**
     * Service method to find movie.
     * 
     * @param id is given movie id
     * @return movie
     * @throws EntityNotFoundException if there is not movie with given id
     */
    public Movie findOne(Long id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Movie movie;

        if ((movie = movieRepository.findById(id).orElse(null)) == null) {
            LOG.log(
                Level.INFO, 
                "User with email: '" + user.getEmail() + "' look up for movie with id: '" 
                    + id + "', which doesn't exist."
            );

            throw new EntityNotFoundException();
        }

        LOG.log(
            Level.INFO, 
            "User with email: '" + user.getEmail() + "' successfully look up for movie with id: '" 
                + movie.getId() + "'."
        );

        return movie;
    }

    /**
     * Service method to delete movie.
     * 
     * @param id is movie id
     * @throws EntityNotFoundException if there is not a movie with given id or id is null
     */
    public void deleteOne(Long id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (id == null) {
            LOG.log(
                Level.INFO, 
                "User with email: '" + user.getEmail() + "' trying to delete movie with id=null." 
            );

            throw new EntityNotFoundException();
        }

        try {
            movieRepository.deleteById(id);
            
            LOG.log(
                Level.INFO, 
                "User with email: '" + user.getEmail() + "' successfully deleted movie with id: '" 
                    + id + "'."
            );
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Service method to create movie.
     * 
     * @param request is Movie DTO for creation
     * @return created movie
     * @throws DataIntegrityViolationException if there is already movie with given name
     */
    public Movie create(CreateMovieRequest request) throws DataIntegrityViolationException {

        Movie movie = movieMapper.toEntity(request);

        saveDirectorBeforeFlushing(movie, request.getDirector());
        saveActorsBeforeFlushing(movie, request.getActors());

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LOG.log(
            Level.INFO, 
            "User with email: '" + user.getEmail() + "' successfully created movie which has id: '" 
                + movie.getId() + "'."
        );

        return movieRepository.save(movie);
    }

    /**
     * Service method to update movie.
     * 
     * @param request is request DTO for updating movie.
     * @return updating movie
     * @throws EntityNotFoundException if movie doesn't exist
     * @throws DataIntegrityViolationException if there is already movie with requested name
     */
    public Movie update(UpdateMovieRequest request) throws DataIntegrityViolationException {

        Movie movie;

        if (movieRepository.findById(request.getId()).isPresent() == false) {
            throw new EntityNotFoundException();
        }

        movie = movieMapper.toEntity(request);

        saveDirectorBeforeFlushing(movie, request.getDirector());
        saveActorsBeforeFlushing(movie, request.getActors());

        movie = movieRepository.save(movie);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LOG.log(
            Level.INFO, 
            "User with email: '" + user.getEmail() + "' successfully updated movie which has id: '" 
                + movie.getId() + "'."
        );

        return movie;
    }

    /**
     * Save director in db if not already exists.
     * 
     * @param movie is Movie entity
     * @param directorReq is CreateDirectorRequest DTO
     */
    private void saveDirectorBeforeFlushing(Movie movie, CreateDirectorRequest directorReq) {

        Director director;

        // if director exists in DB, set him to movie
        if ((director = directorRepository.findByFirstNameAndLastName(directorReq.getFirstName(), directorReq.getLastName())) != null) {
            movie.setDirector(director);
        } 
        // if director not exists, save him first to db, and then set to movie
        else {
            director = directorMapper.toEntity(directorReq);
            director = directorRepository.save(director);

            movie.setDirector(director);
        }
    }

    /**
     * Save actors in db which does not already exist.
     * 
     * @param movie is Movie entity
     * @param actorsRequest is list of CreateActorRequest DTOs
     */
    private void saveActorsBeforeFlushing(Movie movie, List<CreateActorRequest> actorsRequest) {

        List<Actor> actors = new ArrayList<>();

        for (CreateActorRequest actorRequest : actorsRequest) {
            Actor actor;

            if ((actor = actorRepository.findByFirstNameAndLastName(actorRequest.getFirstName(), actorRequest.getLastName())) != null) {
                actors.add(actor);
            } else {
                actor = new Actor();
                actor.setFirstName(actorRequest.getFirstName());
                actor.setLastName(actorRequest.getLastName());

                actor = actorRepository.save(actor);

                actors.add(actor);
            }

            movie.setActors(actors);
        }
    }

    public List<Actor> findAllActorsByMovieId(Long movieId) {

        List<Movie> movies = findAll();
        List<Actor> actors = new ArrayList<>();
        
        movies.forEach(m -> {
            if (m.getId() == movieId) {
                actors.addAll(m.getActors());
            }
        });
        
        return actors;
    }

}
