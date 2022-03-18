package com.movieserver.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.movieserver.constants.MovieConstants;
import com.movieserver.domain.dto.request.CreateActorRequest;
import com.movieserver.domain.dto.request.CreateDirectorRequest;
import com.movieserver.domain.dto.request.UpdateMovieRequest;
import com.movieserver.model.Movie;
import com.movieserver.repository.ActorRepository;
import com.movieserver.repository.DirectorRepository;
import com.movieserver.service.MovieService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UpdateMovieServiceIT {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;
    
    @Test
    public void updateMovie_fieldsOk_ok() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Branka", "Katic"));
        }};

        UpdateMovieRequest request = new UpdateMovieRequest(
            MovieConstants.ID_MOVIE_EXISTS, 
            MovieConstants.ID_MOVIE_EXISTS + " 2", 
            MovieConstants.GENRE_COMEDY, 
            154, 
            director, 
            actors
        );

        // save movie
        Movie movie = movieService.update(request);

        // assert
        assertNotNull(movie);
        assertEquals(movie.getActors().size(), 2);
        assertEquals(movie.getDirector().getFirstName(), director.getFirstName());
        assertEquals(movie.getDirector().getLastName(), director.getLastName());
        assertEquals(movie.getGenre(), MovieConstants.GENRE_COMEDY);

        assertEquals(movieService.findAll().size(), 2);

        assertEquals(actorRepository.findAll().size(), 6);
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateMovie_idNotExists_ok() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Branka", "Katic"));
        }};

        UpdateMovieRequest request = new UpdateMovieRequest(
            MovieConstants.ID_MOVIE_NOT_EXISTS, 
            MovieConstants.CREATE_MOVIE_NAME_OK + " 2", 
            MovieConstants.GENRE_COMEDY, 
            154, 
            director, 
            actors
        );

        // save movie
        movieService.update(request);

        // assert
        assertEquals(actorRepository.findAll().size(), 4);
        assertEquals(directorRepository.findAll().size(), 2);
    }
    
}
