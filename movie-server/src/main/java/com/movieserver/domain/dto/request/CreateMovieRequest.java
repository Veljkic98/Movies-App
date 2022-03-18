package com.movieserver.domain.dto.request;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateMovieRequest {

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

    public CreateMovieRequest() {
    }

    public CreateMovieRequest(String name, String genre, int length, CreateDirectorRequest director, List<CreateActorRequest> actors) {
        this.name = name;
        this.genre = genre;
        this.length = length;
        this.director = director;
        this.actors = actors;
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
            " name='" + getName() + "'" +
            ", genre='" + getGenre() + "'" +
            ", length='" + getLength() + "'" +
            ", directorId='" + getDirector() + "'" +
            "}";
    }

}
