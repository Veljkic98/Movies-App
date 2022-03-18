package com.reportserver.domain.dto;

public class MovieReport {

    private Long id;

    private String name;
    
    private String genre;

    private int length;

    private String directorFirstname;

    private String directorLastname;


    public MovieReport() {
    }

    public MovieReport(Long id, String name, String genre, int length, String directorFirstname, String directorLastname) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.length = length;
        this.directorFirstname = directorFirstname;
        this.directorLastname = directorLastname;
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

    public String getDirectorFirstname() {
        return this.directorFirstname;
    }

    public void setDirectorFirstname(String directorFirstname) {
        this.directorFirstname = directorFirstname;
    }

    public String getDirectorLastname() {
        return this.directorLastname;
    }

    public void setDirectorLastname(String directorLastname) {
        this.directorLastname = directorLastname;
    }
    
}
