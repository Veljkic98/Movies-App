package com.movieserver.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityNotFoundException;

import com.movieserver.constants.MovieConstants;
import com.movieserver.repository.ActorRepository;
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
public class DeleteMovieServiceIT {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ActorRepository actorRepository;

    @Test
    public void deleteMovie_idOk_ok() {

        movieService.deleteOne(MovieConstants.ID_MOVIE_EXISTS);

        // assert
        assertEquals(movieService.findAll().size(), 1);
        assertEquals(actorRepository.findAll().size(), 4);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteMovie_idNotExists_ok() {

        movieService.deleteOne(MovieConstants.ID_MOVIE_NOT_EXISTS);

        // assert
        assertEquals(movieService.findAll().size(), 2);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteMovie_idNotNull_ok() {

        movieService.deleteOne(null);

        // assert
        assertEquals(movieService.findAll().size(), 2);
    }

}
