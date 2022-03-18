package com.movieserver.domain.mapper;

import java.util.List;

import com.movieserver.domain.dto.request.CreateMovieRequest;
import com.movieserver.domain.dto.request.UpdateMovieRequest;
import com.movieserver.domain.dto.response.MovieView;
import com.movieserver.model.Movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieMapper implements Mapper<Movie, MovieView, CreateMovieRequest> {

    @Autowired
    private DirectorMapper directorMapper;
    
    @Autowired
    private ActorMapper actorMapper;

    @Override
    public Movie toEntity(CreateMovieRequest dto) {

        Movie movie = new Movie(
            dto.getName(), 
            dto.getGenre(), 
            dto.getLength(), 
            directorMapper.toEntity(dto.getDirector()), 
            actorMapper.toEntityList(dto.getActors())
        );

        return movie;
    }

    public Movie toEntity(UpdateMovieRequest dto) {

        Movie movie = new Movie(
            dto.getId(),
            dto.getName(), 
            dto.getGenre(), 
            dto.getLength(), 
            directorMapper.toEntity(dto.getDirector()), 
            actorMapper.toEntityList(dto.getActors())
        );

        return movie;
    }

    @Override
    public MovieView toDto(Movie entity) {
        
        MovieView view = new MovieView(
            entity.getId(), 
            entity.getName(), 
            entity.getGenre(),
            entity.getLength(),
            directorMapper.toDto(entity.getDirector()),
            actorMapper.toDtoList(entity.getActors())
        );

        return view;
    }

    @Override
    public List<MovieView> toDtoList(List<Movie> entities) {

        // TODO Auto-generated method stub
        return null;
    }

}
