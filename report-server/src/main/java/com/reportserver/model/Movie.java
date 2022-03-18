package com.reportserver.model;

import java.util.List;

public class Movie {

    private Long id;

    private String name;
    
    private String genre;

    private int length;

    private Director director;

    private List<Actor> actors;

    public Movie() {
    }

    public Movie(Long id, String name, String genre, int length, Director director, List<Actor> actors) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.length = length;
        this.director = director;
        this.actors = actors;
    }

    public Movie(String name, String genre, int length, Director director, List<Actor> actors) {
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

    public Director getDirector() {
        return this.director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return this.actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
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
