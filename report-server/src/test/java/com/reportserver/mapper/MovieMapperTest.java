package com.reportserver.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.reportserver.constants.MovieConstants;
import com.reportserver.domain.dto.MovieReport;
import com.reportserver.domain.mapper.MovieMapper;
import com.reportserver.model.Actor;
import com.reportserver.model.Director;
import com.reportserver.model.Movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MovieMapperTest {

    @InjectMocks
    private MovieMapper mapper;

    @Test
    public void toReport_fieldsOk_movieReport() {

        Movie movie = getMovie();

        MovieReport report = mapper.toReport(movie);

        assertNotNull(report);
        assertEquals(movie.getId(), MovieConstants.MOVIE_ID_EXISTS);
        assertEquals(movie.getGenre(), MovieConstants.MOVIE_GENRE);
        assertEquals(movie.getName(), MovieConstants.MOVIE_NAME);
        assertEquals(movie.getLength(), MovieConstants.MOVIE_LENGTH);
        assertEquals(movie.getActors().size(), 1);
    }

    @Test
    public void toReportList_fieldsOk_movieReportList() {
        
        Movie movie = getMovie();
        
        List<Movie> movies = new ArrayList<>(){{
            add(movie);
            add(movie);
        }};

        List<MovieReport> reports = mapper.toReportList(movies);

        assertNotNull(reports);
        assertEquals(reports.size(), 2);
    }

    /**
     * Help method to return movie.
     * 
     * @return Movie with all fields setted
     */
    private Movie getMovie() {

        Director director = new Director(
            MovieConstants.DIRECTOR_ID_EXISTS, 
            MovieConstants.DIRECTOR_FIRSTNAME, 
            MovieConstants.DIRECTOR_LASTNAME
        );

        List<Actor> actors = new ArrayList<>() {{
            add(
                new Actor(
                    MovieConstants.ACTOR_ID_EXISTS, 
                    MovieConstants.ACTOR_FIRSTNAME, 
                    MovieConstants.ACTOR_LASTNAME
                )
            );
        }};

        return new Movie(
            MovieConstants.MOVIE_ID_EXISTS, 
            MovieConstants.MOVIE_NAME, 
            MovieConstants.MOVIE_GENRE, 
            MovieConstants.MOVIE_LENGTH, 
            director, 
            actors
        );
    }
    
}
