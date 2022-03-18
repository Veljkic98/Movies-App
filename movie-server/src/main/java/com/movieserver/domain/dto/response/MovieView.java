package com.movieserver.domain.dto.response;

import java.util.List;

public class MovieView {

    private Long id;

    private String name;
    
    private String genre;

    private int length;

    private DirectorView director;

    private List<ActorView> actors;

    public MovieView() {
    }

    public MovieView(Long id, String name, String genre, int length, DirectorView director, List<ActorView> actors) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public DirectorView getDirector() {
        return this.director;
    }

    public void setDirector(DirectorView director) {
        this.director = director;
    }

    public List<ActorView> getActors() {
        return this.actors;
    }

    public void setActors(List<ActorView> actors) {
        this.actors = actors;
    }
    
}
