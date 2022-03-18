package com.movieserver.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.movieserver.constants.MovieConstants;
import com.movieserver.model.Movie;
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
public class GetMovieServiceIT {

    @Autowired
    private MovieService movieService;

    @Test
    public void getAll__ok() {
        
        List<Movie> movies = movieService.findAll();

        // assert
        assertEquals(movies.size(), 2);
    }

    @Test
    public void getOne_movieExists_ok() {

        Long id = MovieConstants.ID_MOVIE_EXISTS;

        Movie movie = movieService.findOne(id);

        // assert 
        assertNotNull(movie);
        assertEquals(movie.getId(), id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getOne_movieNotExists_exception() {

        Long id = MovieConstants.ID_MOVIE_NOT_EXISTS;

        movieService.findOne(id);
    }
    
}
