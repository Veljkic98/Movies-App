package com.movieserver.domain.dto.request;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateMovieRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;
    
    @NotNull
    private String genre;

    @NotNull @Min(value = 1)
    private int length;

    @NotNull
    private CreateDirectorRequest director;

    @NotEmpty
    private List<CreateActorRequest> actors;

    public UpdateMovieRequest() {
    }

    public UpdateMovieRequest(Long id, String name, String genre, int length, CreateDirectorRequest director, List<CreateActorRequest> actors) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.length = length;
        this.director = director;
        this.actors = actors;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getGenre() {
        return this.genre;
    }

    public int getLength() {
        return this.length;
    }

    public CreateDirectorRequest getDirector() {
        return this.director;
    }

    public List<CreateActorRequest> getActors() {
        return this.actors;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", genre='" + getGenre() + "'" +
            ", length='" + getLength() + "'" +
            ", director='" + getDirector() + "'" +
            ", actors='" + getActors() + "'" +
            "}";
    }
    
}
