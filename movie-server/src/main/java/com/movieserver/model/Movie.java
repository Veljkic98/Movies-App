package com.movieserver.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
    
    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "length", nullable = false)
    private int length;

    @ManyToOne
    private Director director;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade({ CascadeType.DETACH })
    @JoinTable(
        name = "movie_actor",
        joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "id")
    )
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
