package com.movieserver.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.movieserver.constants.MovieConstants;
import com.movieserver.domain.dto.request.CreateActorRequest;
import com.movieserver.domain.dto.request.CreateDirectorRequest;
import com.movieserver.domain.dto.request.CreateMovieRequest;
import com.movieserver.model.Movie;
import com.movieserver.repository.ActorRepository;
import com.movieserver.repository.DirectorRepository;
import com.movieserver.service.MovieService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class CreateMovieServiceIT {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Test
    public void createMovie_fieldsOkAndDirectorNotExistsAndActorsNotExists_ok() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Branka", "Katic"));
        }};

        CreateMovieRequest request = new CreateMovieRequest(
            MovieConstants.CREATE_MOVIE_NAME_OK, 
            MovieConstants.GENRE_SCI_FI, 
            122, 
            director, 
            actors
        );

        // save movie
        Movie movie = movieService.create(request);

        // assert
        assertNotNull(movie);
        assertEquals(movie.getActors().size(), 2);
        assertEquals(movie.getDirector().getFirstName(), director.getFirstName());
        assertEquals(movie.getDirector().getLastName(), director.getLastName());
        assertEquals(movie.getGenre(), MovieConstants.GENRE_SCI_FI);

        assertEquals(movieService.findAll().size(), 3);

        // director not exists so should be added to db
        assertEquals(directorRepository.findAll().size(), 3);

        assertEquals(actorRepository.findAll().size(), 6);
    }

    @Test
    public void createMovie_fieldsOkAndDirectorNotExistsAndActorsExists_ok() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Lane", "Gutovic"));
            add(new CreateActorRequest("Milos", "Bikovic"));
        }};

        CreateMovieRequest request = new CreateMovieRequest(
            MovieConstants.CREATE_MOVIE_NAME_OK, 
            MovieConstants.GENRE_SCI_FI, 
            122, 
            director, 
            actors
        );

        // save movie
        Movie movie = movieService.create(request);

        // assert
        assertNotNull(movie);
        assertEquals(movie.getActors().size(), 2);
        assertEquals(movie.getDirector().getFirstName(), director.getFirstName());
        assertEquals(movie.getDirector().getLastName(), director.getLastName());
        assertEquals(movie.getGenre(), MovieConstants.GENRE_SCI_FI);

        assertEquals(movieService.findAll().size(), 3);

        // director not exists so should be added to db
        assertEquals(directorRepository.findAll().size(), 3);

        assertEquals(actorRepository.findAll().size(), 4);
    }

    @Test
    public void createMovie_fieldsOkAndDirectorNotExistsAndOneActorExistOneNot_ok() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Milos", "Bikovic"));
        }};

        CreateMovieRequest request = new CreateMovieRequest(
            MovieConstants.CREATE_MOVIE_NAME_OK, 
            MovieConstants.GENRE_SCI_FI, 
            122, 
            director, 
            actors
        );

        // save movie
        Movie movie = movieService.create(request);

        // assert
        assertNotNull(movie);
        assertEquals(movie.getActors().size(), 2);
        assertEquals(movie.getDirector().getFirstName(), director.getFirstName());
        assertEquals(movie.getDirector().getLastName(), director.getLastName());
        assertEquals(movie.getGenre(), MovieConstants.GENRE_SCI_FI);

        assertEquals(movieService.findAll().size(), 3);

        // director not exists so should be added to db
        assertEquals(directorRepository.findAll().size(), 3);

        assertEquals(actorRepository.findAll().size(), 5);
    }

    @Test
    public void createMovie_fieldsOkAndDirectorExists_ok() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("John", "Ford");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Branka", "Katic"));
        }};

        CreateMovieRequest request = new CreateMovieRequest(
            MovieConstants.CREATE_MOVIE_NAME_OK, 
            MovieConstants.GENRE_SCI_FI, 
            122, 
            director, 
            actors
        );

        // save movie
        Movie movie = movieService.create(request);

        // assert
        assertNotNull(movie);
        assertEquals(movie.getActors().size(), 2);
        assertEquals(movie.getDirector().getFirstName(), director.getFirstName());
        assertEquals(movie.getDirector().getLastName(), director.getLastName());
        assertEquals(movie.getGenre(), MovieConstants.GENRE_SCI_FI);

        assertEquals(movieService.findAll().size(), 3);

        // director not exists so should be added to db
        assertEquals(directorRepository.findAll().size(), 2);

        // assertEquals(actorRepository.findAll().size(), 6);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createMovie_nameExists_exception() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Milos", "Bikovic"));
        }};

        CreateMovieRequest request = new CreateMovieRequest(
            MovieConstants.CREATE_MOVIE_NAME_NOT_OK, 
            MovieConstants.GENRE_SCI_FI, 
            122, 
            director, 
            actors
        );

        // save movie
        movieService.create(request);

        assertEquals(movieService.findAll().size(), 2);
        assertEquals(actorRepository.findAll().size(), 4);
        assertEquals(directorRepository.findAll().size(), 2);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createMovie_nameNull_exception() {

        // create request data
        CreateDirectorRequest director = new CreateDirectorRequest("Martin", "Scorcese");

        List<CreateActorRequest> actors = new ArrayList<>(){{
            add(new CreateActorRequest("Nikola", "Kojo"));
            add(new CreateActorRequest("Branka", "Katic"));
        }};

        CreateMovieRequest request = new CreateMovieRequest(
            null, 
            MovieConstants.GENRE_SCI_FI, 
            122, 
            director, 
            actors
        );

        // save movie
        movieService.create(request);

        // assert
        assertEquals(movieService.findAll().size(), 2);
        assertEquals(actorRepository.findAll().size(), 4);
        assertEquals(directorRepository.findAll().size(), 2);
    }
    
}
