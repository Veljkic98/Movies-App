package com.movieserver.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import com.movieserver.domain.dto.request.CreateActorRequest;
import com.movieserver.domain.dto.request.CreateDirectorRequest;
import com.movieserver.domain.dto.request.CreateMovieRequest;
import com.movieserver.domain.dto.request.UpdateMovieRequest;
import com.movieserver.domain.dto.response.ActorView;
import com.movieserver.domain.dto.response.DirectorView;
import com.movieserver.domain.dto.response.MovieView;
import com.movieserver.domain.mapper.ActorMapper;
import com.movieserver.domain.mapper.DirectorMapper;
import com.movieserver.domain.mapper.MovieMapper;
import com.movieserver.model.Actor;
import com.movieserver.model.Director;
import com.movieserver.model.Movie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MovieMapperTest {

    @InjectMocks
    private MovieMapper mapper;

    @Mock
    private DirectorMapper directorMapper;

    @Mock
    private ActorMapper actorMapper;

    @Test
    public void toEntity_createMovieRequestOK_movie() {

        CreateMovieRequest request = new CreateMovieRequest(
            "Avatar", 
            "sci-fy", 
            99, 
            new CreateDirectorRequest("Dusko", "Dugousko"), 
            new ArrayList<CreateActorRequest>() {{
                add(new CreateActorRequest("Veljko", "Veljkovic"));
            }}
        );

        when(directorMapper.toEntity(request.getDirector())).thenReturn(
            new Director(
                request.getDirector().getFirstName(), 
                request.getDirector().getLastName()
            )
        );

        when(actorMapper.toEntityList(request.getActors())).thenReturn(
            new ArrayList<>() {{
                add(new Actor("Veljko", "Veljkovic"));
            }}
        );

        Movie movie = mapper.toEntity(request);

        // assert
        assertNotNull(movie);
        assertEquals(movie.getName(), request.getName());
        assertEquals(movie.getGenre(), request.getGenre());
        assertEquals(movie.getLength(), request.getLength());
        assertNotNull(movie.getDirector());
        assertEquals(movie.getDirector().getFirstName(), request.getDirector().getFirstName());
        assertEquals(movie.getDirector().getLastName(), request.getDirector().getLastName());
        assertEquals(movie.getActors().size(), request.getActors().size());
    }

    @Test
    public void toEntity_updateMovieRequestOK_movie() {

        UpdateMovieRequest request = new UpdateMovieRequest(
            1L, 
            "Avatar", 
            "sci-fy", 
            99, 
            new CreateDirectorRequest("Dusko", "Dugousko"), 
            new ArrayList<CreateActorRequest>() {{
                add(new CreateActorRequest("Veljko", "Veljkovic"));
            }}
        );

        when(directorMapper.toEntity(request.getDirector())).thenReturn(
            new Director(
                request.getDirector().getFirstName(), 
                request.getDirector().getLastName()
            )
        );

        when(actorMapper.toEntityList(request.getActors())).thenReturn(
            new ArrayList<>() {{
                add(new Actor("Veljko", "Veljkovic"));
            }}
        );

        Movie movie = mapper.toEntity(request);

        // assert
        assertNotNull(movie);
        assertEquals(movie.getName(), request.getName());
        assertEquals(movie.getGenre(), request.getGenre());
        assertEquals(movie.getLength(), request.getLength());
        assertNotNull(movie.getDirector());
        assertEquals(movie.getDirector().getFirstName(), request.getDirector().getFirstName());
        assertEquals(movie.getDirector().getLastName(), request.getDirector().getLastName());
        assertEquals(movie.getActors().size(), request.getActors().size());
    }

    @Test
    public void toDto_entityOk_movieView() {

        Movie movie = new Movie(
            1L, 
            "name", 
            "genre", 
            33, 
            new Director(1L, "firstName", "lastName"), 
            new ArrayList<>() {{
                add(new Actor(1L, "Veljko", "Veljkovic"));
                add(new Actor(2L, "Dragan", "Duskovic"));
                add(new Actor(3L, "Nikola", "Radojcic"));
            }}
        );

        when(directorMapper.toDto(movie.getDirector())).thenReturn(
            new DirectorView(
                movie.getId(),
                movie.getDirector().getFirstName(), 
                movie.getDirector().getLastName()
            )
        );

        when(actorMapper.toDtoList(movie.getActors())).thenReturn(
            new ArrayList<>() {{
                add(new ActorView(1L, "Veljko", "Veljkovic"));
                add(new ActorView(2L, "Dragan", "Duskovic"));
                add(new ActorView(3L, "Nikola", "Radojcic"));
            }}
        );

        MovieView dto = mapper.toDto(movie);

        // assert
        assertNotNull(dto);
        assertEquals(movie.getGenre(), dto.getGenre());
        assertEquals(movie.getActors().size(), dto.getActors().size());
        assertEquals(movie.getDirector().getFirstName(), dto.getDirector().getFirstName());
        assertEquals(movie.getDirector().getLastName(), dto.getDirector().getLastName());
        assertEquals(movie.getDirector().getId(), dto.getDirector().getId());
        assertEquals(movie.getLength(), dto.getLength());
        assertEquals(movie.getName(), dto.getName());
    }
    
}
